package com.hospital.controller;

import com.hospital.entity.Patient;
import com.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Optional;
import com.hospital.entity.Doctor;
import com.hospital.service.DoctorService;
import com.hospital.entity.BaseUser;
import com.hospital.entity.Admin;
import com.hospital.util.PasswordUtil;
import com.hospital.service.AdminService;

@Controller
public class AuthController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstName,
                               @RequestParam(required = false) String middleName,
                               @RequestParam String lastName,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam String dateOfBirth,
                               @RequestParam String gender,
                               @RequestParam String address,
                               @RequestParam String username,
                               @RequestParam String userType,
                               Model model) {
        try {
            if (userType.equals("PATIENT")) {
                Patient patient = new Patient();
                patient.setFirstName(firstName);
                patient.setMiddleName(middleName);
                patient.setLastName(lastName);
                patient.setEmail(email);
                patient.setPhone(phone);
                patient.setDateOfBirth(LocalDate.parse(dateOfBirth));
                patient.setGender(BaseUser.Gender.valueOf(gender.toUpperCase()));
                patient.setAddress(address);
                patient.setUsername(username);
                patient.setPassword(password);
                patientService.savePatient(patient);
            } else if (userType.equals("DOCTOR")) {
                Doctor doctor = new Doctor();
                doctor.setFirstName(firstName);
                doctor.setMiddleName(middleName);
                doctor.setLastName(lastName);
                doctor.setEmail(email);
                doctor.setPhone(phone);
                doctor.setDateOfBirth(LocalDate.parse(dateOfBirth));
                doctor.setGender(BaseUser.Gender.valueOf(gender.toUpperCase()));
                doctor.setAddress(address);
                doctor.setUsername(username);
                doctor.setPassword(password);
                // Set default or dummy values for required doctor-specific fields
                doctor.setSpecialty("General");
                doctor.setLicenseNumber("LIC" + System.currentTimeMillis());
                doctor.setExperience(1);
                doctorService.saveDoctor(doctor);
            } else if (userType.equals("ADMIN")) {
                Admin admin = new Admin();
                admin.setFirstName(firstName);
                admin.setMiddleName(middleName);
                admin.setLastName(lastName);
                admin.setEmail(email);
                admin.setPhone(phone);
                admin.setDateOfBirth(LocalDate.parse(dateOfBirth));
                admin.setGender(BaseUser.Gender.valueOf(gender.toUpperCase()));
                admin.setAddress(address);
                admin.setUsername(username);
                admin.setPassword(password);
                adminService.saveAdmin(admin);
            } else {
                model.addAttribute("error", "Invalid user type selected");
                return "register";
            }
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String userType,
                        Model model,
                        HttpSession session) {
        System.out.println("[DEBUG] Login attempt: username=" + username + ", userType=" + userType);
        if (userType.equals("PATIENT")) {
            Optional<Patient> patientOpt = patientService.getPatientByUsername(username);
            if (patientOpt.isPresent() && patientOpt.get().getPassword().equals(PasswordUtil.hashPassword(password))) {
                Patient patient = patientOpt.get();
                    session.setAttribute("user", patient);
                    System.out.println("[DEBUG] Patient login successful: " + username);
                    return "redirect:/patient/dashboard";
            } else {
                System.out.println("[DEBUG] Patient login failed: user not found");
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } else if (userType.equals("DOCTOR")) {
            // Try finding doctor by username, then email
            Optional<Doctor> doctorOpt = doctorService.getDoctorByUsername(username);
            if (doctorOpt.isEmpty()){
                doctorOpt = doctorService.getDoctorByEmail(username);
            }

            if (doctorOpt.isPresent() && doctorOpt.get().getPassword().equals(PasswordUtil.hashPassword(password))) {
                Doctor doctor = doctorOpt.get();
                session.setAttribute("user", doctor);
                System.out.println("[DEBUG] Doctor login successful: " + username);
                return "redirect:/doctor/dashboard";
            } else {
                System.out.println("[DEBUG] Doctor login failed: user not found or wrong password");
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } else if (userType.equals("ADMIN")) {
            Optional<Admin> adminOpt = adminService.getAdminByUsername(username);
            if(adminOpt.isPresent() && adminOpt.get().getPassword().equals(PasswordUtil.hashPassword(password))) {
                session.setAttribute("user", adminOpt.get());
                System.out.println("[DEBUG] Admin login successful: " + username);
                return "redirect:/admin/dashboard";
            } else {
                System.out.println("[DEBUG] Admin login failed: user not found or wrong password");
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } else {
            System.out.println("[DEBUG] Login failed: invalid user type");
            model.addAttribute("error", "Invalid user type selected");
            return "login";
        }
    }
} 