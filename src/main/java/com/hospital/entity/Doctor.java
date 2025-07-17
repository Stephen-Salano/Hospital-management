package com.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Specialty is required")
    @Size(min = 2, max = 100, message = "Specialty must be between 2 and 100 characters")
    @Column(nullable = false)
    private String specialty;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+254|0)?7\\d{8}$", message = "Please provide a valid Kenyan phone number")
    @Column(nullable = false)
    private String phone;
    
    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @NotBlank(message = "License number is required")
    @Size(min = 5, max = 20, message = "License number must be between 5 and 20 characters")
    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;
    
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience cannot exceed 50 years")
    @Column(nullable = false)
    private Integer experience;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String username;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Doctor() {}
    
    public Doctor(String fullName, String specialty, String email, String phone, 
                  Gender gender, String licenseNumber, Integer experience, String password) {
        this.specialty = specialty;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.licenseNumber = licenseNumber;
        this.experience = experience;
        this.password = password;
    }

    // Gender enum
    public enum Gender {
        MALE, FEMALE, OTHER
    }
} 