package com.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "doctors")
public class Doctor extends BaseUser {
    @NotBlank(message = "Specialty is required")
    @Size(min = 2, max = 100, message = "Specialty must be between 2 and 100 characters")
    @Column(nullable = false)
    private String specialty;

    @NotBlank(message = "License number is required")
    @Size(min = 5, max = 20, message = "License number must be between 5 and 20 characters")
    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;

    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience cannot exceed 50 years")
    @Column(nullable = false)
    private Integer experience;

    // A doctor belongs to one department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @PrePersist
    protected void onCreate() {
        setCreatedAt(java.time.LocalDateTime.now());
        setUpdatedAt(java.time.LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdatedAt(java.time.LocalDateTime.now());
    }
}