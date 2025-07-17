package com.hospital.repository;

import com.hospital.entity.Patient;
import com.hospital.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Find all prescriptions for a specific patient
    List<Prescription> findByPatientOrderByIssueDateDesc(Patient patient);
}