package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public Appointment saveAppointment(Appointment appointment) {
        // Validate that the doctor exists
        if (appointment.getDoctor() == null || appointment.getDoctor().getId() == null) {
            throw new RuntimeException("Doctor is required for appointment booking.");
        }
        
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + appointment.getDoctor().getId()));
        
        appointment.setDoctor(doctor);
        
        // Check if the appointment date is in the past
        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot book appointments in the past.");
        }
        
        // Check if the appointment date is today and time has passed
        if (appointment.getAppointmentDate().equals(LocalDate.now()) && 
            appointment.getAppointmentTime().isBefore(LocalTime.now())) {
            throw new RuntimeException("Cannot book appointments in the past.");
        }
        
        // Check if the time slot is already booked for this doctor
        if (appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(
                doctor, appointment.getAppointmentDate(), appointment.getAppointmentTime())) {
            throw new RuntimeException("This time slot is already booked. Please choose a different time.");
        }
        
        // Set default status if not set
        if (appointment.getStatus() == null) {
            appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        }
        
        return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctorOrderByAppointmentDateDescAppointmentTimeDesc(doctor);
    }
    
    public List<Appointment> getAppointmentsByPatientEmail(String patientEmail) {
        return appointmentRepository.findByPatientEmailOrderByAppointmentDateDescAppointmentTimeDesc(patientEmail);
    }
    
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointmentRepository.findByStatusOrderByAppointmentDateDescAppointmentTimeDesc(status);
    }
    
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDateOrderByAppointmentTime(date);
    }
    
    public List<Appointment> getTodayAppointments() {
        return appointmentRepository.findTodayAppointments();
    }
    
    public List<Appointment> getUpcomingAppointments() {
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        return appointmentRepository.findUpcomingAppointments(nextWeek);
    }
    
    public Appointment updateAppointmentStatus(Long appointmentId, Appointment.AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + appointmentId));
        
                
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
    
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }
    
    public long getAppointmentCountByStatus(Appointment.AppointmentStatus status) {
        return appointmentRepository.countByStatus(status);
    }
    
    public long getAppointmentCountByDoctor(Doctor doctor) {
        return appointmentRepository.countByDoctor(doctor);
    }
    
    public boolean isTimeSlotAvailable(Doctor doctor, LocalDate date, LocalTime time) {
        return !appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(doctor, date, time);
    }
    
    public List<Appointment> getAppointmentsByDoctorAndDateRange(Doctor doctor, LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByDoctorAndDateRange(doctor, startDate, endDate);
    }
    
    // Helper method to format appointment time for display
    public String formatAppointmentTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
    }
    
    // Helper method to get available time slots for a doctor on a specific date
    public List<LocalTime> getAvailableTimeSlots(Doctor doctor, LocalDate date) {
        List<LocalTime> allTimeSlots = List.of(
            LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0),
            LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0), LocalTime.of(17, 0)
        );
        
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorAndAppointmentDateOrderByAppointmentTime(doctor, date);
        List<LocalTime> bookedTimes = existingAppointments.stream()
                .map(Appointment::getAppointmentTime)
                .toList();
        
        return allTimeSlots.stream()
                .filter(time -> !bookedTimes.contains(time))
                .toList();
    }
} 