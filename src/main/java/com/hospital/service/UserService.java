package com.hospital.service;

import com.hospital.entity.User;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
} 