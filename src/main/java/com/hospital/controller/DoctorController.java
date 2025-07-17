package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.Prescription;
import com.hospital.entity.MedicalRecord;
import com.hospital.service.*;
import com.hospital.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private DoctorService doctorService;

    @ModelAttribute("user")
    public Doctor addUserToModel(HttpSession session) {
        return (Doctor) session.getAttribute("user");
    }

    @GetMapping("/dashboard")
    public String doctorDashboard(@ModelAttribute("user") Doctor doctor, Model model) {
        if (doctor == null) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "dashboard");
        return "doctor/dashboard";
    }

    @GetMapping("/schedule")
    public String viewMySchedule(@ModelAttribute("user") Doctor doctor, Model model) {
        if (doctor == null) {
            return "redirect:/login";
        }
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor);
        model.addAttribute("appointments", appointments);
        model.addAttribute("activePage", "schedule");
        return "doctor/schedule";
    }

    @GetMapping("/patients")
    public String viewMyPatients(@ModelAttribute("user") Doctor doctor, Model model) {
        if (doctor == null) {
            return "redirect:/login";
        }
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        model.addAttribute("activePage", "patients");
        return "doctor/patients";
    }

    @GetMapping("/profile")
    public String doctorProfile(@ModelAttribute("user") Doctor doctor, Model model) {
        if (doctor == null) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "profile");
        return "doctor/profile";
    }

    @PostMapping("/profile")
    public String updateDoctorProfile(@ModelAttribute("user") Doctor doctor,
                                      @RequestParam String email,
                                      @RequestParam String phone,
                                      @RequestParam String address,
                                      RedirectAttributes redirectAttributes,
                                      HttpSession session) {
        if (doctor == null) {
            return "redirect:/login";
        }
        try {
            doctor.setEmail(email);
            doctor.setPhone(phone);
            doctor.setAddress(address);
            doctorService.updateDoctor(doctor);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        return "redirect:/doctor/profile";
    }

    @GetMapping("/write-prescription")
    public String showPrescriptionForm(@ModelAttribute("user") Doctor doctor, Model model) {
        if (doctor == null) {
            return "redirect:/login";
        }
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("activePage", "write-prescription");
        return "doctor/write-prescriptions"; // Filename fixed here
    }

    @PostMapping("/write-prescription")
    public String savePrescription(@ModelAttribute("user") Doctor doctor,
                                   @RequestParam Long patientId,
                                   @RequestParam String medication,
                                   @RequestParam String dosage,
                                   @RequestParam String frequency,
                                   @RequestParam String duration,
                                   @RequestParam(required = false) String notes,
                                   RedirectAttributes redirectAttributes) {
        if (doctor == null) {
            return "redirect:/login";
        }
        try {
            Patient patient = patientService.getPatientById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            Prescription prescription = new Prescription();
            prescription.setPatient(patient);
            prescription.setDoctor(doctor);
            prescription.setMedication(medication);
            prescription.setDosage(dosage);
            prescription.setFrequency(frequency);
            prescription.setDuration(duration);
            prescription.setNotes(notes);
            prescription.setIssueDate(LocalDate.now());

            prescriptionService.savePrescription(prescription);
            redirectAttributes.addFlashAttribute("success", "Prescription for " + patient.getFullName() + " saved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving prescription: " + e.getMessage());
        }
        return "redirect:/doctor/write-prescription";
    }

    @GetMapping("/write-record")
    public String showMedicalRecordForm(@ModelAttribute("user") Doctor doctor, Model model) {
        if (doctor == null) {
            return "redirect:/login";
        }
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("activePage", "write-record");
        return "doctor/write-record";
    }

    @PostMapping("/write-record")
    public String saveMedicalRecord(@ModelAttribute("user") Doctor doctor,
                                    @RequestParam Long patientId,
                                    @RequestParam String medicalNotes,
                                    RedirectAttributes redirectAttributes) {
        if (doctor == null) {
            return "redirect:/login";
        }
        try {
            Patient patient = patientService.getPatientById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            MedicalRecord record = new MedicalRecord();
            record.setPatient(patient);
            record.setDoctor(doctor);
            record.setNotes(medicalNotes);

            medicalRecordService.saveRecord(record);
            redirectAttributes.addFlashAttribute("success", "Medical note for " + patient.getFullName() + " added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding medical note: " + e.getMessage());
        }
        return "redirect:/doctor/write-record";
    }
}