package com.hospital.controller;

import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.Appointment;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import com.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        List<Patient> patients = patientService.getAllPatients();
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
        return "admin/dashboard";
    }

    @GetMapping("/add-doctor")
    public String addDoctor() {
        return "admin/add-doctor";
    }

    @PostMapping("/save-doctor")
    public String saveDoctor(
            @RequestParam String fullName,
            @RequestParam String specialty,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String gender,
            @RequestParam String licenseNumber,
            @RequestParam int experience,
            @RequestParam String password,
            Model model) {
        
        try {
            // Sanitize inputs (trim whitespace)
            fullName = fullName.trim();
            specialty = specialty.trim();
            email = email.trim().toLowerCase();
            phone = phone.trim();
            gender = gender.trim();
            licenseNumber = licenseNumber.trim();
            
            // Create Doctor entity
            Doctor doctor = new Doctor();
            doctor.setFirstName(fullName.split(" ")[0]);
            if (fullName.split(" ").length > 2) doctor.setMiddleName(fullName.split(" ")[1]);
            doctor.setLastName(fullName.split(" ")[fullName.split(" ").length-1]);
            doctor.setSpecialty(specialty);
            doctor.setEmail(email);
            doctor.setPhone(phone);
            doctor.setGender(Doctor.Gender.valueOf(gender.toUpperCase()));
            doctor.setLicenseNumber(licenseNumber);
            doctor.setExperience(experience);
            doctor.setPassword(password);
            
            // Save doctor
            Doctor savedDoctor = doctorService.saveDoctor(doctor);
            
            String[] nameParts = fullName.split(" ");
            String first = nameParts[0];
            String last = nameParts[nameParts.length-1];
            model.addAttribute("success", "Doctor " + first + " " + last + " has been successfully registered!");
            
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while registering the doctor. Please try again.");
        }
        
        return "admin/add-doctor";
    }

    @PostMapping("/save-patient")
    public String savePatient(
            @RequestParam String firstName,
            @RequestParam String middleName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String gender,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(required = false) String medicalNotes,
            Model model) {
        
        try {
            // Sanitize inputs (trim whitespace)
            firstName = firstName.trim();
            middleName = middleName.trim();
            lastName = lastName.trim();
            email = email.trim().toLowerCase();
            phone = phone.trim();
            gender = gender.trim();
            address = address.trim();
            medicalNotes = medicalNotes != null ? medicalNotes.trim() : "";
            
            // Parse date
            LocalDate dob = LocalDate.parse(dateOfBirth);
            
            // Create Patient entity
            Patient patient = new Patient();
            patient.setFirstName(firstName);
            patient.setMiddleName(middleName);
            patient.setLastName(lastName);
            patient.setDateOfBirth(dob);
            patient.setGender(Patient.Gender.valueOf(gender.toUpperCase()));
            patient.setEmail(email);
            patient.setPhone(phone);
            patient.setAddress(address);
            patient.setMedicalNotes(medicalNotes.isEmpty() ? null : medicalNotes);
            
            // Save patient
            Patient savedPatient = patientService.savePatient(patient);
            
            model.addAttribute("success", "Patient " + savedPatient.getFullName() + " has been successfully registered!");
            
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while registering the patient. Please try again.");
        }
        
        return "admin/add-doctor";
    }

    @GetMapping("/add-patient")
    public String addPatient() {
        return "admin/add-patient";
    }

    @GetMapping("/verify-doctor")
    public String verifyDoctor() {
        return "admin/verify-doctor";
    }

    @PostMapping("/verify-doctor")
    public String verifyDoctor(
            @RequestParam(required = false) String license,
            @RequestParam(required = false) String email,
            Model model) {
        
        try {
            // Sanitize inputs
            license = license != null ? license.trim() : "";
            email = email != null ? email.trim().toLowerCase() : "";
            
            // Validate that at least one field is provided
            if (license.isEmpty() && email.isEmpty()) {
                model.addAttribute("error", "Please provide either a license number or email address.");
                return "admin/verify-doctor";
            }
            
            Doctor doctor = null;
            
            // Search by license number if provided
            if (!license.isEmpty()) {
                doctor = doctorService.findByLicenseNumber(license);
            }
            
            // Search by email if license not found and email provided
            if (doctor == null && !email.isEmpty()) {
                doctor = doctorService.findByEmail(email);
            }
            
            if (doctor != null) {
                model.addAttribute("doctor", doctor);
                model.addAttribute("success", "Doctor found successfully!");
            } else {
                model.addAttribute("error", "No doctor found with the provided information.");
            }
            
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while verifying the doctor. Please try again.");
        }
        
        return "admin/verify-doctor";
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        // Get all appointments for admin view
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        List<Appointment> todayAppointments = appointmentService.getTodayAppointments();
        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointments();
        
        // Get appointment counts by status
        long pendingCount = appointmentService.getAppointmentCountByStatus(Appointment.AppointmentStatus.PENDING);
        long confirmedCount = appointmentService.getAppointmentCountByStatus(Appointment.AppointmentStatus.CONFIRMED);
        long completedCount = appointmentService.getAppointmentCountByStatus(Appointment.AppointmentStatus.COMPLETED);
        long cancelledCount = appointmentService.getAppointmentCountByStatus(Appointment.AppointmentStatus.CANCELLED);
        
        model.addAttribute("allAppointments", allAppointments);
        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("confirmedCount", confirmedCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("cancelledCount", cancelledCount);
        
        return "admin/appointments";
    }

    @PostMapping("/update-appointment-status")
    public String updateAppointmentStatus(
            @RequestParam Long appointmentId,
            @RequestParam String status,
            Model model) {
        
        try {
            Appointment.AppointmentStatus appointmentStatus = Appointment.AppointmentStatus.valueOf(status.toUpperCase());
            appointmentService.updateAppointmentStatus(appointmentId, appointmentStatus);
            model.addAttribute("success", "Appointment status updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update appointment status: " + e.getMessage());
        }
        
        return "redirect:/admin/appointments";
    }

    @PostMapping("/delete-appointment")
    public String deleteAppointment(
            @RequestParam Long appointmentId,
            Model model) {
        
        try {
            appointmentService.deleteAppointment(appointmentId);
            model.addAttribute("success", "Appointment deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete appointment: " + e.getMessage());
        }
        
        return "redirect:/admin/appointments";
    }

    @GetMapping("/delete-records")
    public String deleteRecords() {
        return "admin/delete-records";
    }

    @GetMapping("/profile")
    public String profile() {
        return "admin/profile";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "admin/accounts";
    }

    @GetMapping("/settings")
    public String settings() {
        return "admin/settings";
    }
} 