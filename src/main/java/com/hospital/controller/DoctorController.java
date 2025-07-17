package com.hospital.controller;

import com.hospital.entity.Doctor;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorController {
    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("user");
        if (doctor == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", doctor);
        model.addAttribute("activePage", "dashboard");
        return "doctor/dashboard";
    }
} 