package com.hospital.model;

import java.util.Date;

public class Prescription {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private Date prescriptionDate;
    private String medication;
    private String dosage;
    private String frequency;
    private String duration;
    private String notes;

    public Prescription(int id, Patient patient, Doctor doctor, Date prescriptionDate,
                       String medication, String dosage, String frequency, String duration, String notes) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.prescriptionDate = prescriptionDate;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", patient=" + patient.getFirstName() + " " + patient.getLastName() +
                ", doctor=" + doctor.getFirstName() + " " + doctor.getLastName() +
                ", prescriptionDate=" + prescriptionDate +
                ", medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", frequency='" + frequency + '\'' +
                ", duration='" + duration + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 