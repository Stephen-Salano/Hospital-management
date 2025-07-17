package com.hospital.service;

import com.hospital.entity.Admin;
import com.hospital.util.PasswordUtil;
import com.hospital.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin saveAdmin(Admin admin) {
        // Hash password before saving
        admin.setPassword(PasswordUtil.hashPassword(admin.getPassword()));
        return adminRepository.save(admin);
    }
} 