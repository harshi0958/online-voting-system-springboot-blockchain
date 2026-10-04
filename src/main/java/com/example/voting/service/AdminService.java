package com.example.voting.service;

import com.example.voting.entity.Admin;
import com.example.voting.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public String login(String username, String password) {

        Admin admin = adminRepository
                .findByUsernameAndPassword(username, password)
                .orElse(null);

        if (admin == null) {
            return "Invalid Admin Credentials";
        }

        return "Admin Login Successful";
    }
}
