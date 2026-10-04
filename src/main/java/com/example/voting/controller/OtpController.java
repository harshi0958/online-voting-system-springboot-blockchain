package com.example.voting.controller;

import com.example.voting.service.OtpService;
import com.example.voting.service.SmsService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;   // ✅ FIX

    // SEND OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String mobile){

        String otp = otpService.generateOtp(mobile);
        smsService.sendOtp(mobile, otp);

        return "OTP Sent Successfully";
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp,
                            HttpSession session){

        boolean isValid = otpService.verifyOtp(mobile, otp);

        if(isValid){

            String sql = "SELECT aadhaar_no FROM voter WHERE mobile=?";

            List<String> list = jdbcTemplate.queryForList(sql, String.class, mobile);

            if(list.isEmpty()){
                return "Mobile not registered";
            }

            String aadhaarNo = list.get(0);

            // 🔥 YAHI ADD KARNA THA
            session.setAttribute("aadhaarNo", aadhaarNo);
            session.setAttribute("mobile", mobile);

            System.out.println("SESSION Aadhaar: " + aadhaarNo);
            System.out.println("SESSION Mobile: " + mobile);

            return "Login Successful";
        }

        return "Invalid OTP";
    }
}