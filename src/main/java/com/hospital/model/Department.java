package com.hospital.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private int id;
    private String name;
    private String description;
    private Doctor headDoctor;
    private List<Doctor> doctors;

    public Department(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.doctors = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Doctor getHeadDoctor() {
        return headDoctor;
    }

    public void setHeadDoctor(Doctor headDoctor) {
        this.headDoctor = headDoctor;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", headDoctor=" + (headDoctor != null ? headDoctor.getFirstName() + " " + headDoctor.getLastName() : "None") +
                ", doctors=" + doctors.size() +
                '}';
    }
} 