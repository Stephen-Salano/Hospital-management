package com.hospital;

import com.hospital.model.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class HospitalManagementSystem {
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Appointment> appointments;
    private List<Department> departments;
    private List<Prescription> prescriptions;
    private Scanner scanner;

    public HospitalManagementSystem() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        appointments = new ArrayList<>();
        departments = new ArrayList<>();
        prescriptions = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Add Department");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Add Prescription");
            System.out.println("6. View All Patients");
            System.out.println("7. View All Doctors");
            System.out.println("8. View All Departments");
            System.out.println("9. View All Appointments");
            System.out.println("10. View All Prescriptions");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    addDoctor();
                    break;
                case 3:
                    addDepartment();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    addPrescription();
                    break;
                case 6:
                    viewAllPatients();
                    break;
                case 7:
                    viewAllDoctors();
                    break;
                case 8:
                    viewAllDepartments();
                    break;
                case 9:
                    viewAllAppointments();
                    break;
                case 10:
                    viewAllPrescriptions();
                    break;
                case 11:
                    running = false;
                    System.out.println("Thank you for using Hospital Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private void addPatient() {
        System.out.println("\n=== Add New Patient ===");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
        String dobStr = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Blood Group: ");
        String bloodGroup = scanner.nextLine();
        System.out.print("Enter Medical History: ");
        String medicalHistory = scanner.nextLine();

        try {
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
            Patient patient = new Patient(
                patients.size() + 1,
                firstName,
                lastName,
                email,
                phone,
                address,
                patientId,
                dateOfBirth,
                gender,
                bloodGroup,
                medicalHistory
            );
            patients.add(patient);
            System.out.println("Patient added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    private void addDoctor() {
        System.out.println("\n=== Add New Doctor ===");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Enter License Number: ");
        String licenseNumber = scanner.nextLine();
        System.out.print("Enter Years of Experience: ");
        int yearsOfExperience = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Doctor doctor = new Doctor(
            doctors.size() + 1,
            firstName,
            lastName,
            email,
            phone,
            address,
            specialization,
            licenseNumber,
            yearsOfExperience
        );
        doctors.add(doctor);
        System.out.println("Doctor added successfully!");
    }

    private void addDepartment() {
        System.out.println("\n=== Add New Department ===");
        System.out.print("Enter Department Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department Description: ");
        String description = scanner.nextLine();

        Department department = new Department(departments.size() + 1, name, description);
        departments.add(department);
        System.out.println("Department added successfully!");
    }

    private void scheduleAppointment() {
        if (patients.isEmpty() || doctors.isEmpty()) {
            System.out.println("Cannot schedule appointment: No patients or doctors available!");
            return;
        }

        System.out.println("\n=== Schedule Appointment ===");
        System.out.println("Select Patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getFirstName() + " " + patients.get(i).getLastName());
        }
        System.out.print("Enter patient number: ");
        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        System.out.println("Select Doctor:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).getFirstName() + " " + doctors.get(i).getLastName() + 
                             " (" + doctors.get(i).getSpecialization() + ")");
        }
        System.out.print("Enter doctor number: ");
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();

        try {
            Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Appointment appointment = new Appointment(
                appointments.size() + 1,
                patients.get(patientIndex),
                doctors.get(doctorIndex),
                appointmentDate,
                "Scheduled",
                notes
            );
            appointments.add(appointment);
            System.out.println("Appointment scheduled successfully!");
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    private void addPrescription() {
        if (patients.isEmpty() || doctors.isEmpty()) {
            System.out.println("Cannot add prescription: No patients or doctors available!");
            return;
        }

        System.out.println("\n=== Add New Prescription ===");
        System.out.println("Select Patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getFirstName() + " " + patients.get(i).getLastName());
        }
        System.out.print("Enter patient number: ");
        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        System.out.println("Select Doctor:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).getFirstName() + " " + doctors.get(i).getLastName());
        }
        System.out.print("Enter doctor number: ");
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Medication: ");
        String medication = scanner.nextLine();
        System.out.print("Enter Dosage: ");
        String dosage = scanner.nextLine();
        System.out.print("Enter Frequency: ");
        String frequency = scanner.nextLine();
        System.out.print("Enter Duration: ");
        String duration = scanner.nextLine();
        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();

        try {
            Date prescriptionDate = new Date();
            Prescription prescription = new Prescription(
                prescriptions.size() + 1,
                patients.get(patientIndex),
                doctors.get(doctorIndex),
                prescriptionDate,
                medication,
                dosage,
                frequency,
                duration,
                notes
            );
            prescriptions.add(prescription);
            System.out.println("Prescription added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding prescription: " + e.getMessage());
        }
    }

    private void viewAllPatients() {
        System.out.println("\n=== All Patients ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private void viewAllDoctors() {
        System.out.println("\n=== All Doctors ===");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private void viewAllDepartments() {
        System.out.println("\n=== All Departments ===");
        if (departments.isEmpty()) {
            System.out.println("No departments registered.");
            return;
        }
        for (Department department : departments) {
            System.out.println(department);
        }
    }

    private void viewAllAppointments() {
        System.out.println("\n=== All Appointments ===");
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    private void viewAllPrescriptions() {
        System.out.println("\n=== All Prescriptions ===");
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available.");
            return;
        }
        for (Prescription prescription : prescriptions) {
            System.out.println(prescription);
        }
    }

    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem();
        system.start();
    }
} 