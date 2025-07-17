package com.hospital.model;

import java.util.Date;

public class Appointment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private Date appointmentDate;
    private String status;
    private String notes;

    public Appointment(int id, Patient patient, Doctor doctor, Date appointmentDate, String status, String notes) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.status = status;
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

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient.getFirstName() + " " + patient.getLastName() +
                ", doctor=" + doctor.getFirstName() + " " + doctor.getLastName() +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 