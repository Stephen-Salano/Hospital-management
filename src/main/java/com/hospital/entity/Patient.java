package com.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "patients")
public class Patient extends BaseUser {
    @Column(name = "medical_notes", columnDefinition = "TEXT")
    private String medicalNotes;

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