package com.hospital.service;

import com.hospital.entity.Doctor;
import com.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public Doctor saveDoctor(Doctor doctor) {
        // Check if email already exists
        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new RuntimeException("Email already registered: " + doctor.getEmail());
        }
        
        // Check if license number already exists
        if (doctorRepository.existsByLicenseNumber(doctor.getLicenseNumber())) {
            throw new RuntimeException("License number already registered: " + doctor.getLicenseNumber());
        }
        
        // Check if phone already exists
        if (doctorRepository.existsByPhone(doctor.getPhone())) {
            throw new RuntimeException("Phone number already registered: " + doctor.getPhone());
        }
        
        // Hash password
        doctor.setPassword(hashPassword(doctor.getPassword()));
        
        return doctorRepository.save(doctor);
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
    
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
    
    public Optional<Doctor> getDoctorByLicenseNumber(String licenseNumber) {
        return doctorRepository.findByLicenseNumber(licenseNumber);
    }
    
    public Doctor updateDoctor(Doctor doctor) {
        if (!doctorRepository.existsById(doctor.getId())) {
            throw new RuntimeException("Doctor not found with ID: " + doctor.getId());
        }
        return doctorRepository.save(doctor);
    }
    
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }
    
    public boolean existsByLicenseNumber(String licenseNumber) {
        return doctorRepository.existsByLicenseNumber(licenseNumber);
    }
    
    public boolean existsByPhone(String phone) {
        return doctorRepository.existsByPhone(phone);
    }
    
    public Doctor findByLicenseNumber(String licenseNumber) {
        return doctorRepository.findByLicenseNumber(licenseNumber)
                .orElse(null);
    }
    
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .orElse(null);
    }
    
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
} 