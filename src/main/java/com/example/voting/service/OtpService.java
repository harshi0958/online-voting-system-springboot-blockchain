package com.example.voting.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class OtpService {

    private HashMap<String, String> otpStorage = new HashMap<>();
    private HashMap<String, Long> otpTime = new HashMap<>();

    public String generateOtp(String mobile){

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        otpStorage.put(mobile, otp);
        otpTime.put(mobile, System.currentTimeMillis());

        System.out.println("OTP for " + mobile + " : " + otp); // testing

        return otp;
    }

    public boolean verifyOtp(String mobile, String otp){

        if(!otpStorage.containsKey(mobile)){
            return false;
        }

        long time = otpTime.get(mobile);

        // ⏱ 2 min expiry
        if(System.currentTimeMillis() - time > 120000){
            otpStorage.remove(mobile);
            return false;
        }

        return otpStorage.get(mobile).equals(otp);
    }
}