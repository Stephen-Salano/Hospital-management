package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import com.hospital.entity.Prescription;
import com.hospital.entity.MedicalRecord;
import com.hospital.service.AppointmentService;
import com.hospital.service.PatientService;
import com.hospital.service.PrescriptionService;
import com.hospital.service.MedicalRecordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PatientService patientService;

    // This method runs before any other in this controller,
    // and its return value is added to the model.
    @ModelAttribute("user")
    public Patient addUserToModel(HttpSession session) {
        return (Patient) session.getAttribute("user");
    }

    @GetMapping("/dashboard")
    public String patientDashboard(@ModelAttribute("user") Patient patient, Model model) {
        if (patient == null) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "dashboard");
        return "patient/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(@ModelAttribute("user") Patient patient, Model model){
        if (patient == null){
            return "redirect:/login";
        }
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patient.getId());
        model.addAttribute("appointments", appointments);
        model.addAttribute("activePage", "appointments");
        return "patient/appointments";
    }

    @GetMapping("/profile")
    public String patientProfile(@ModelAttribute("user") Patient patient, Model model) {
        if (patient == null) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "profile");
        return "patient/profile";
    }

    @PostMapping("/profile")
    public String updatePatientProfile(@ModelAttribute("user") Patient patient,
                                       @RequestParam String email,
                                       @RequestParam String phone,
                                       @RequestParam String address,
                                       RedirectAttributes redirectAttributes,
                                       HttpSession session) {
        if (patient == null) {
            return "redirect:/login";
        }
        try {
            patient.setEmail(email);
            patient.setPhone(phone);
            patient.setAddress(address);
            Patient updatedPatient = patientService.updatePatient(patient);
            session.setAttribute("user", updatedPatient);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        return "redirect:/patient/profile";
    }

    @GetMapping("/prescriptions")
    public String viewPrescriptions(@ModelAttribute("user") Patient patient, Model model){
        if (patient == null){
            return "redirect:/login";
        }
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patient);
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("activePage", "prescriptions");
        return "patient/prescriptions";
    }

    @GetMapping("/view-records")
    public String viewMedicalRecords(@ModelAttribute("user") Patient patient, Model model){
        if (patient == null){
            return "redirect:/login";
        }
        List<MedicalRecord> records = medicalRecordService.getRecordsByPatient(patient);
        model.addAttribute("medicalRecords", records);
        model.addAttribute("activePage", "view-records");
        return "patient/view-records";
    }
} 