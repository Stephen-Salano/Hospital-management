package com.hospital.repository;

import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // Find appointments by doctor
    List<Appointment> findByDoctorOrderByAppointmentDateDescAppointmentTimeDesc(Doctor doctor);
    
    // Find appointments by patient email
    List<Appointment> findByPatientEmailOrderByAppointmentDateDescAppointmentTimeDesc(String patientEmail);
    
    // Find appointments by status
    List<Appointment> findByStatusOrderByAppointmentDateDescAppointmentTimeDesc(Appointment.AppointmentStatus status);
    
    // Find appointments by date
    List<Appointment> findByAppointmentDateOrderByAppointmentTime(LocalDate appointmentDate);
    
    // Find appointments by doctor and date
    List<Appointment> findByDoctorAndAppointmentDateOrderByAppointmentTime(Doctor doctor, LocalDate appointmentDate);
    
    // Find appointments by doctor and date range
    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND a.appointmentDate BETWEEN :startDate AND :endDate ORDER BY a.appointmentDate, a.appointmentTime")
    List<Appointment> findByDoctorAndDateRange(
            @Param("doctor") Doctor doctor,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    // Check if appointment exists for doctor, date, and time
    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime);
    
    // Count appointments by status
    long countByStatus(Appointment.AppointmentStatus status);
    
    // Count appointments by doctor
    long countByDoctor(Doctor doctor);
    
    // Find today's appointments
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = CURRENT_DATE ORDER BY a.appointmentTime")
    List<Appointment> findTodayAppointments();
    
    // Find upcoming appointments (next 7 days)
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN CURRENT_DATE AND :nextWeek")
    List<Appointment> findUpcomingAppointments(@Param("nextWeek") LocalDate nextWeek);
} 