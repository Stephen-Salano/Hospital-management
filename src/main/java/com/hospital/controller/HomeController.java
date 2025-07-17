package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
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
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "booking-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(
            @RequestParam String patientName,
            @RequestParam String patientPhone,
            @RequestParam String patientEmail,
            @RequestParam int patientAge,
            @RequestParam Long doctorId,
            @RequestParam String appointmentDate,
            @RequestParam String appointmentTime,
            @RequestParam String appointmentType,
            @RequestParam(required = false) String symptoms,
            Model model) {
        
        try {
            // Sanitize inputs
            patientName = patientName.trim();
            patientPhone = patientPhone.trim();
            patientEmail = patientEmail.trim().toLowerCase();
            symptoms = symptoms != null ? symptoms.trim() : "";
            
            // Parse date and time
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);
            
            // Create Appointment entity
            Appointment appointment = new Appointment();
            appointment.setPatientName(patientName);
            appointment.setPatientPhone(patientPhone);
            appointment.setPatientEmail(patientEmail);
            appointment.setPatientAge(patientAge);
            appointment.setAppointmentDate(date);
            appointment.setAppointmentTime(time);
            appointment.setAppointmentType(Appointment.AppointmentType.valueOf(appointmentType));
            appointment.setSymptoms(symptoms.isEmpty() ? null : symptoms);
            
            // Set doctor
            Doctor doctor = doctorService.getDoctorById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            appointment.setDoctor(doctor);
            
            // Save appointment
            Appointment savedAppointment = appointmentService.saveAppointment(appointment);
            
            model.addAttribute("success", "Appointment booked successfully! Your appointment ID is: " + savedAppointment.getId());
            
            // Re-add doctors for the form
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
            
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            
            // Re-add doctors for the form
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while booking the appointment. Please try again.");
            
            // Re-add doctors for the form
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
        }
        
        return "booking-appointment";
    }
} 