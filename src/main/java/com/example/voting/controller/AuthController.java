package com.example.voting.controller;

import com.example.voting.service.OtpService;
import com.example.voting.service.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private SmsService smsService;

    // SEND OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String mobile){

        String otp = otpService.generateOtp(mobile);

        smsService.sendOtp(mobile, otp);

        return "OTP Sent Successfully";
    }

    // VERIFY OTP + LOGIN
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp){

        boolean valid = otpService.verifyOtp(mobile, otp);

        if(valid){
            return "Login Successful";
        }else{
            return "Invalid or Expired OTP";
        }
    }
}