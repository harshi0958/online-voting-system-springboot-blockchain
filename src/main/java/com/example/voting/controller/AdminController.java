package com.example.voting.controller;

import com.example.voting.entity.Candidate;
import com.example.voting.repository.CandidateRepository;
import com.example.voting.repository.VoterRepository;
import com.example.voting.service.VotingService;
import com.example.voting.service.VotingSessionService;
import com.example.voting.blockchain.Block;
import com.example.voting.blockchain.Blockchain;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AdminController {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VotingService votingService;

    // ✅ VOTING SESSION SERVICE
    @Autowired
    private VotingSessionService votingSessionService;
    
    @Autowired
    private Blockchain blockchain;

    // 🔗 GET BLOCKCHAIN
    

    // ================= ADMIN LOGIN =================
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        if (username.equals("admin") && password.equals("Admin@2026Secure")) {

            session.setAttribute("adminUser", username);

            System.out.println("SESSION ID: " + session.getId());   // 🔥 ADD
            System.out.println("SESSION VALUE: " + session.getAttribute("adminUser"));

            return "success";
        }

        return "fail";
    }

    // ================= CHECK SESSION =================
    @GetMapping("/check")
    public String checkSession(HttpSession session) {

        System.out.println("CHECK SESSION ID: " + session.getId());
        System.out.println("CHECK VALUE: " + session.getAttribute("adminUser"));

        if (session.getAttribute("adminUser") != null) {
            return "active";
        }
        return "inactive";
    }

    // ================= DASHBOARD DATA =================
    @GetMapping("/dashboard")
    public Map<String, Long> getDashboard(HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            throw new RuntimeException("Unauthorized");
        }

        long totalVoters = voterRepository.count();
        long voted = voterRepository.countByHasVotedTrue();
        long remaining = totalVoters - voted;

        Map<String, Long> data = new HashMap<>();
        data.put("totalVoters", totalVoters);
        data.put("voted", voted);
        data.put("remaining", remaining);

        return data;
    }


    // ================= ADD CANDIDATE =================
    @PostMapping("/addCandidate")
    public Candidate addCandidate(@RequestBody Candidate candidate,
                                  HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            throw new RuntimeException("Unauthorized");
        }

        return candidateRepository.save(candidate);
    }


    // ================= GET ALL CANDIDATES =================
    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates(HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            throw new RuntimeException("Unauthorized");
        }

        return candidateRepository.findAll();
    }


    // ================= DELETE CANDIDATE =================
    @DeleteMapping("/deleteCandidate/{id}")
    public String deleteCandidate(@PathVariable Integer id,
                                  HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "Unauthorized";
        }

        candidateRepository.deleteById(id);
        return "Deleted Successfully";
    }


    // ================= RESULTS =================
    @GetMapping("/results")
    public List<Candidate> getResults() {

        return candidateRepository.findAll();
    }


    // ================= BLOCKCHAIN VIEW =================
    @GetMapping("/blockchain")
    public List<Block> getBlockchain(){
        return blockchain.getChain();
    }


    // ================= VERIFY BLOCKCHAIN =================
    @GetMapping("/verifyBlockchain")
    public String verifyBlockchain() {
        return blockchain.isChainValid() ? "Blockchain VALID" : "Blockchain TAMPERED";
    }


 // ================= START VOTING =================
    @PostMapping("/startVoting")
    public String startVoting(HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "Unauthorized";
        }

        votingSessionService.startVoting();
        return "Voting Started";
    }


    // ================= STOP VOTING =================
    @PostMapping("/stopVoting")
    public String stopVoting(HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "Unauthorized";
        }

        votingSessionService.stopVoting();
        return "Voting Stopped";
    }


    // ================= CHECK VOTING STATUS =================
    @GetMapping("/votingStatus")
    public boolean votingStatus(){

        return votingSessionService.isVotingActive();

    }
    // ================= ADMIN LOGOUT =================
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "logout";
    }

}