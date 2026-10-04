package com.example.voting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.voting.entity.Voter;
import com.example.voting.repository.VoterRepository;
import com.example.voting.service.EmailService;
import com.example.voting.service.VotingSessionService;

import org.springframework.jdbc.core.JdbcTemplate;
import jakarta.servlet.http.HttpSession;
import com.example.voting.blockchain.Blockchain;

import java.io.File;
import java.util.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/voter")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class VoterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VotingSessionService votingSessionService;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private Blockchain blockchain;

    // ================= REGISTER =================
    @PostMapping("/register")
    public String register(
            @RequestParam String aadhaarNo,
            @RequestParam String name,
            @RequestParam String mobile,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String dob,
            @RequestParam("photo") MultipartFile photo
    ) {
        try {
            if (photo.isEmpty()) {
                return "Photo is missing!";
            }

            // ✅ AGE CHECK
            java.time.LocalDate birthDate = java.time.LocalDate.parse(dob);
            java.time.LocalDate today = java.time.LocalDate.now();
            int age = java.time.Period.between(birthDate, today).getYears();

            if (age < 18) {
                return "You are not eligible for voting. Thank you.";
            }

            // ✅ PASSWORD CHECK
            if (!isValidPassword(password)) {
                return "Password must be 8 characters with 1 uppercase, 1 lowercase, 1 digit and 1 special character";
            }

            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(uploadDir + fileName);
            photo.transferTo(file);

            String sql = "INSERT INTO voter (aadhaar_no, name, mobile, email, password, photo, has_voted) VALUES (?, ?, ?, ?, ?, ?, false)";

            jdbcTemplate.update(sql,
                    aadhaarNo, name, mobile, email, password, fileName);

            emailService.sendRegistrationEmail(email, name);

            return "Registration Successful";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$");
    }
    
    
    // ================= LOGIN =================
    @PostMapping("/login")
    public String login(@RequestParam String aadhaarNo,
                        @RequestParam String password,
                        HttpSession session) {

        String sql = "SELECT COUNT(*) FROM voter WHERE aadhaar_no=? AND password=?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                aadhaarNo,
                password
        );

        if (count != null && count > 0) {
            session.setAttribute("aadhaarNo", aadhaarNo);
            return "Login Successful";
        } else {
            return "Invalid Aadhaar or Password";
        }
    }

    // ================= FORGOT PASSWORD (SEND OTP) =================
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {

        Optional<Voter> voterOpt = voterRepository.findByEmail(email);

        if(voterOpt.isEmpty()){
            return "Email not registered!";
        }

        Voter voter = voterOpt.get();

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        voter.setOtp(otp);
        voter.setOtpExpiry(java.time.LocalDateTime.now().plusMinutes(5));

        voterRepository.save(voter);

        emailService.sendOtpEmail(email, otp);

        return "OTP sent to email";
    }

    // ================= VERIFY OTP =================
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        Optional<Voter> voterOpt = voterRepository.findByEmail(email);

        if(voterOpt.isEmpty()){
            return "Invalid email";
        }

        Voter voter = voterOpt.get();

        if(voter.getOtp() == null){
            return "OTP not generated";
        }

        if(!voter.getOtp().equals(otp)){
            return "Invalid OTP";
        }

        if(voter.getOtpExpiry().isBefore(java.time.LocalDateTime.now())){
            return "OTP expired";
        }

        return "OTP Verified";
    }

    // ================= RESET PASSWORD =================
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {

        Optional<Voter> voterOpt = voterRepository.findByEmail(email);
        if (!isValidPassword(newPassword)) {
            return "Weak Password!";
        }
        
        if(voterOpt.isEmpty()){
            return "User not found";
        }

        Voter voter = voterOpt.get();

        voter.setPassword(newPassword);
        voter.setOtp(null);
        voter.setOtpExpiry(null);

        voterRepository.save(voter);

        return "Password Reset Successful";
    
    }

    // ================= GET CANDIDATES =================
    @GetMapping("/candidates")
    public List<Map<String, Object>> getCandidates() {
        String sql = "SELECT id, name, party_name FROM candidate";
        return jdbcTemplate.queryForList(sql);
    }

    // ================= CAST VOTE =================
    @PostMapping("/vote/{candidateId}")
    public String vote(@PathVariable int candidateId, HttpSession session) {

        String aadhaarNo = (String) session.getAttribute("aadhaarNo");

        if (aadhaarNo == null) {
            return "Please Login First";
        }

        String checkSql = "SELECT has_voted FROM voter WHERE aadhaar_no=?";
        Boolean hasVoted = jdbcTemplate.queryForObject(checkSql, Boolean.class, aadhaarNo);

        if (hasVoted != null && hasVoted) {
            return "You Already Voted!";
        }

        // ✅ DATABASE UPDATE
        jdbcTemplate.update("UPDATE candidate SET vote_count = vote_count + 1 WHERE id=?", candidateId);
        jdbcTemplate.update("UPDATE voter SET has_voted=true WHERE aadhaar_no=?", aadhaarNo);

        // 🔥 BLOCKCHAIN ENTRY (YAHI ADD KARNA THA)
        String voteData = "Voter " + aadhaarNo + " voted to Candidate " + candidateId;
        blockchain.addBlock(voteData);

        return "Vote Successful";
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged Out Successfully";
    }

    // ================= PROFILE =================
    @GetMapping("/profile")
    public Voter getProfile(HttpSession session){

        String aadhaarNo = (String) session.getAttribute("aadhaarNo");

        if(aadhaarNo == null){
            throw new RuntimeException("Session Expired");
        }

        return voterRepository.findById(aadhaarNo)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================= PHOTO =================
    @GetMapping("/photo/{aadhaarNo}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String aadhaarNo) {
        try {
            Optional<Voter> voterOpt = voterRepository.findById(aadhaarNo);

            if(voterOpt.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            String fileName = voterOpt.get().getPhoto();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File file = new File(uploadDir + fileName);

            if(!file.exists()){
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = java.nio.file.Files.readAllBytes(file.toPath());

            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(imageBytes);

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    //==========UPDATE PROFILE================
 // ================= UPDATE PROFILE =================
    @PostMapping("/update-profile")
    public ResponseEntity<String> updateProfile(
            @RequestParam String email,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            HttpSession session) {

        String aadhaarNo = (String) session.getAttribute("aadhaarNo");

        if (aadhaarNo == null) {
            return ResponseEntity.badRequest().body("Session expired");
        }

        Optional<Voter> voterOpt = voterRepository.findById(aadhaarNo);

        if (voterOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Voter voter = voterOpt.get();

        // ✅ UPDATE EMAIL
        voter.setEmail(email);

        // ✅ UPDATE PHOTO (same logic as register)
        if (photo != null && !photo.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                String uploadDir = System.getProperty("user.dir") + "/uploads/";

                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                File file = new File(uploadDir + fileName);
                photo.transferTo(file);

                voter.setPhoto(fileName);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Photo update failed");
            }
        }

        voterRepository.save(voter);

        return ResponseEntity.ok("Profile updated successfully");
        
        
    }
    @PostMapping("/verify-face")
    public String verifyFace(
            @RequestParam("livePhoto") MultipartFile livePhoto,
            HttpSession session) {

        try {

            String aadhaarNo = (String) session.getAttribute("aadhaarNo");

            if(aadhaarNo == null){
                return "Session expired";
            }

            // GET STORED PHOTO
            Optional<Voter> voterOpt = voterRepository.findById(aadhaarNo);

            if(voterOpt.isEmpty()){
                return "User not found";
            }

            Voter voter = voterOpt.get();

            String storedFileName = voter.getPhoto();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File storedFile = new File(uploadDir + storedFileName);

            if(!storedFile.exists()){
                return "Stored image not found";
            }

            // SAVE LIVE IMAGE TEMP
            String liveFileName = "live_" + System.currentTimeMillis() + ".jpg";
            File liveFile = new File(uploadDir + liveFileName);
            livePhoto.transferTo(liveFile);

            // 🔥 TEMP LOGIC (for demo)
            // future: real face match
            long storedSize = storedFile.length();
            long liveSize = liveFile.length();

            long diff = Math.abs(storedSize - liveSize);

            if(diff < 50000){ // approx match
                return "Success";
            }else{
                return "Unknown person";
            }

        } catch(Exception e){
            e.printStackTrace();
            return "Error in face verification";
        }
    }
}