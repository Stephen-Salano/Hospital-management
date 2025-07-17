package com.hospital.service;

import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    public Patient savePatient(Patient patient) {
        // Check if email already exists
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new RuntimeException("Email already registered: " + patient.getEmail());
        }
        
        // Check if phone already exists
        if (patientRepository.existsByPhone(patient.getPhone())) {
            throw new RuntimeException("Phone number already registered: " + patient.getPhone());
        }
        
        // Hash password before saving
        patient.setPassword(hashPassword(patient.getPassword()));
        return patientRepository.save(patient);
    }
    
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    
    public Patient updatePatient(Patient patient) {
        if (!patientRepository.existsById(patient.getId())) {
            throw new RuntimeException("Patient not found with ID: " + patient.getId());
        }
        return patientRepository.save(patient);
    }
    
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }
    
    public boolean existsByPhone(String phone) {
        return patientRepository.existsByPhone(phone);
    }
    
    public Optional<Patient> getPatientByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    // Helper to hash password, consistent with other services
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