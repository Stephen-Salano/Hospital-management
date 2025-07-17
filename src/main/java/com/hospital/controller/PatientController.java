package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import com.hospital.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String patientDashboard(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("user");
        if (patient == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", patient);
        model.addAttribute("activePage", "dashboard");
        return "patient/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(HttpSession session, Model model){
        Patient patient = (Patient) session.getAttribute("user");
        if (patient == null){
            return "redirect:/login";
        }
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientEmail(patient.getEmail());
        model.addAttribute("appointments", appointments);
        model.addAttribute("activePage", "appointments");
        return "patient/appointments";
    }

    @GetMapping("/profile")
    public String patientProfile(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("user");
        if (patient == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", patient);
        model.addAttribute("activePage", "profile");
        return "patient/profile";
    }

    @GetMapping("/prescriptions")
    public String viewPrescriptions(HttpSession session, Model model){
        Patient patient = (Patient) session.getAttribute("user");
        if (patient == null){
            return "redirect:/login";
        }
        model.addAttribute("prescriptions", Collections.emptyList());
        model.addAttribute("activePage", "prescriptions");
        return "patient/prescriptions";

    }

    @GetMapping("/view-records")
    public String viewMedicalRecords(HttpSession session, Model model){
        Patient patient = (Patient) session.getAttribute("user");
        if (patient == null){
            return "redirect:/login";
        }
        model.addAttribute("user", patient);
        model.addAttribute("activePage", "view-records");
        return "patient/view-records"; // This should match the new page name if you create one
    }
} 