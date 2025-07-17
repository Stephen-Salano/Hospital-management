# Savannah Healthcare - Hospital Management System

A comprehensive, full-stack hospital management web application built using Java, Spring Boot, and Thymeleaf. This system provides a robust platform for managing patients, doctors, and appointments with role-based access for administrators, doctors, and patients.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Setup and Installation](#setup-and-installation)
  - [1. Clone the Repository](#1-clone-the-repository)
  - [2. Set Up the Database](#2-set-up-the-database)
  - [3. Configure the Application](#3-configure-the-application)
  - [4. Run the Application](#4-run-the-application)
- [Usage and Roles](#usage-and-roles)
  - [User Roles & Default Credentials](#user-roles--default-credentials)
  - [Key Routes](#key-routes)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Features

The application is divided into three main user roles, each with a dedicated portal:

### 👨‍⚕️ Patient Portal
- **Dashboard**: A quick overview of key actions and personal information.
- **Profile Management**: View and update personal contact information.
- **Appointment Viewing**: See a list of all past and upcoming appointments.
- **Medical Records**: View a chronological timeline of medical notes from doctors.
- **Prescriptions**: Access a complete list of all prescribed medications.

### 👩‍⚕️ Doctor Portal
- **Dashboard**: Quick access to core doctor functionalities.
- **Schedule Management**: View a list of all assigned appointments.
- **Patient Management**: View a list of all patients in the system.
- **Write Prescriptions**: A dedicated form to create and issue new prescriptions to patients.
- **Write Medical Notes**: Add new entries to a patient's medical record.
- **Profile Management**: Update professional and contact details.

### ⚙️ Admin Portal
- **Dashboard**: Analytics overview with charts and user counts.
- **User Registration**: A unified form to register new doctors and patients.
- **Appointment Management**: A comprehensive view of all appointments in the system.
- **Doctor Verification**: A tool to look up and verify doctor credentials.

### Public Pages
- **Public Website**: Includes Home, About, Services, and Contact pages.
- **User Registration**: A public form for self-registration of any user type.
- **Appointment Booking**: A public form to book an appointment by selecting a patient and doctor.

## Technology Stack

- **Backend**: Java 21, Spring Boot 3, Spring Security, Spring Data JPA (Hibernate)
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Database**: MySQL 8
- **Build Tool**: Apache Maven
- **Server**: Embedded Apache Tomcat

## Prerequisites

Ensure you have the following software installed on your local machine:
- **Java Development Kit (JDK)**: Version 21 or later.
- **Apache Maven**: Version 3.8 or later.
- **MySQL Server**: Version 8.0 or later.
- **An IDE**: IntelliJ IDEA or VS Code is recommended.

## Setup and Installation

Follow these steps to get the project running on your local machine.

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd Hospitl-management
```

### 2. Set Up the Database

The application is configured to work with a MySQL database.

1.  **Start MySQL Server**: Make sure your local MySQL server is running.
2.  **Log into MySQL**: Open a terminal or command prompt and log in as the root user.
    ```bash
    mysql -u root -p
    ```
    Enter your root password when prompted.

3.  **Create the Database**: Run the following SQL command to create the database schema that the application will use.
    ```sql
    CREATE DATABASE hospital;
    ```

### 3. Configure the Application

The database connection details are stored in `src/main/resources/application-dev.properties`.

1.  **Navigate to the properties file**:
    `C:/Users/xirla/Documents/Code/School/Hospitl-management/src/main/resources/application-dev.properties`

2.  **Set the Database Password**: Find the following line in the file:
    ```properties
    spring.datasource.password=${DB_PASSWORD}
    ```

    You have two options to set your password:

    -   **(Recommended & Secure)** Set an environment variable named `DB_PASSWORD` to your MySQL root password. Spring Boot will automatically pick it up.
    -   **(Alternative)** Replace `${DB_PASSWORD}` directly with your password (e.g., `spring.datasource.password=your_secret_password`). **Warning**: If you do this, be careful not to commit this file to a public repository. The `.gitignore` file is already configured to ignore `application-dev.properties` to prevent this.

### 4. Run the Application

You can run the application in two ways:

1.  **Using Maven (Terminal)**:
    Open a terminal in the project's root directory and run:
    ```bash
    mvn spring-boot:run
    ```

2.  **Using an IDE**:
    - Open the project in your IDE (like IntelliJ or VS Code).
    - Locate the `HospitalManagementApplication.java` file.
    - Right-click and select "Run" or "Debug".

The application will start, and you can access it at **http://localhost:8080**.

> **Note**: The application is configured with `spring.jpa.hibernate.ddl-auto=create-drop`. This means the database tables are dropped and recreated every time the application starts, and the sample data from `data.sql` is re-inserted. This is ideal for development.

## Usage and Roles

The application has three distinct roles with pre-configured users for testing.

### User Roles & Default Credentials

| Role    | Username        | Password      |
|---------|-----------------|---------------|
| Admin   | `admin.user`    | `password123` |
| Doctor  | `doc.jones`     | `password123` |
| Patient | `patient.one`   | `password123` |

### Key Routes

- **Public Access**:
  - `/`: Home page.
  - `/login`: User login page.
  - `/register`: Public registration form.
  - `/book-appointment`: Public appointment booking form.
- **Patient Portal**: `http://localhost:8080/patient/**`
  - `/patient/dashboard`
  - `/patient/profile`
  - `/patient/appointments`
- **Doctor Portal**: `http://localhost:8080/doctor/**`
  - `/doctor/dashboard`
  - `/doctor/schedule`
  - `/doctor/write-prescription`
- **Admin Portal**: `http://localhost:8080/admin/**`
  - `/admin/dashboard`
  - `/admin/add-doctor` (which also includes the patient form)
  - `/admin/appointments`

## Database Schema

The application uses the following main entities to model its data:
-   **`BaseUser`**: An abstract class containing common fields for all users (`id`, `firstName`, `email`, `password`, etc.).
-   **`Patient`**: Extends `BaseUser` and adds `medicalNotes`.
-   **`Doctor`**: Extends `BaseUser` and adds `specialty`, `licenseNumber`, and `experience`.
-   **`Admin`**: Extends `BaseUser`.
-   **`Appointment`**: Links a `Patient` and a `Doctor` for a specific date and time.
-   **`Prescription`**: A record of medication prescribed by a `Doctor` to a `Patient`.
-   **`MedicalRecord`**: A timestamped note written by a `Doctor` for a `Patient`.
-   **`Department`**: Represents a hospital department to which a `Doctor` can belong.

## Contributing

Contributions are welcome! If you'd like to contribute, please fork the repository and create a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
