package com.hospital.controller;

import com.hospital.entity.Patient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {
    @GetMapping("/patient/dashboard")
    public String patientDashboard(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("user");
        if (patient == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", patient);
        return "patient/dashboard";
    }
} 