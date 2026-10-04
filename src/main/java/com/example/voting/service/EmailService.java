package com.example.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // ✅ Registration Email
    public void sendRegistrationEmail(String toEmail, String name) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("Registration Successful - Online Voting System");

        msg.setText(
                "Hello " + name + ",\n\n" +
                "You are successfully registered in the Online Voting System.\n\n" +
                "You can now login and cast your vote.\n\n" +
                "Thank you!"
        );

        mailSender.send(msg);
    }

    // ✅ OTP Email (Forgot Password)
    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("OTP for Password Reset");

        msg.setText(
                "Your OTP for password reset is: " + otp + "\n\n" +
                "This OTP is valid for 5 minutes.\n\n" +
                "If you did not request this, please ignore this email."
        );

        mailSender.send(msg);
    }
}