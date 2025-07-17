package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.service.PatientService;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "about/index";
    }

    @GetMapping("/services")
    public String services() {
        return "services/index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact/index";
    }

    @GetMapping("/appointments")
    public String appointments() {
        return "appointments/index";
    }

    @GetMapping("/book-appointment")
    public String bookAppointment(Model model) {
        // Get all available doctors for the dropdown
        populateBookingFormModel(model);
        return "booking-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            @RequestParam String appointmentType,
            @RequestParam(required = false) String symptoms,
            Model model) {
        
        try {
            symptoms = symptoms != null ? symptoms.trim() : "";
            
            // Parse date and time
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);

            // Get patient and doctor
            Patient patient = patientService.getPatientById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            Doctor doctor = doctorService.getDoctorById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            // Create Appointment entity
            Appointment appointment = new Appointment();
            appointment.setAppointmentDate(date);
            appointment.setAppointmentTime(time);
            appointment.setAppointmentType(Appointment.AppointmentType.valueOf(appointmentType));
            appointment.setSymptoms(symptoms.isEmpty() ? null : symptoms);
            
            // Set doctor
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            
            // Save appointment
            Appointment savedAppointment = appointmentService.saveAppointment(appointment);
            
            model.addAttribute("success", "Appointment booked successfully! Your appointment ID is: " + savedAppointment.getId());
            
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while booking the appointment. Please try again.");
        }
        
        // Always re-populate the form data
        populateBookingFormModel(model);
        return "booking-appointment";
    }

    private void populateBookingFormModel(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("todayDate", LocalDate.now().toString());
    }
} 