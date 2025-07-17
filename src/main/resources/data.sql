-- This script is executed on startup to populate the database with initial data.
-- The default password for all users is 'password123'
-- The SHA-256 hash for 'password123' is ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f

-- Create Default Admin
INSERT INTO admin (username, password, first_name, last_name, date_of_birth, gender, email, phone, address, created_at, updated_at)
VALUES ('admin', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Admin', 'User', '1990-01-01', 'OTHER', 'admin@hospital.com', '0700000000', '123 Hospital Lane', NOW(), NOW());

-- Create Default Doctors
INSERT INTO doctors (username, password, first_name, last_name, date_of_birth, gender, email, phone, address, specialty, license_number, experience, created_at, updated_at)
VALUES
('doc.jones', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'John', 'Jones', '1985-05-20', 'MALE', 'john.jones@hospital.com', '0711111111', '101 Medical Plaza', 'Cardiology', 'LIC101', 10, NOW(), NOW()),
('doc.smith', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Jane', 'Smith', '1988-09-12', 'FEMALE', 'jane.smith@hospital.com', '0711111112', '102 Medical Plaza', 'Neurology', 'LIC102', 8, NOW(), NOW()),
('doc.chen', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Li', 'Chen', '1990-02-28', 'MALE', 'li.chen@hospital.com', '0711111113', '103 Medical Plaza', 'Pediatrics', 'LIC103', 5, NOW(), NOW());

-- Create Default Patients
INSERT INTO patients (username, password, first_name, last_name, date_of_birth, gender, email, phone, address, medical_notes, created_at, updated_at)
VALUES
('patient.one', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Alice', 'Wonder', '1992-08-15', 'FEMALE', 'alice.wonder@email.com', '0722222222', '202 Patient St', 'Allergic to penicillin.', NOW(), NOW()),
('patient.two', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Bob', 'Builder', '1989-11-30', 'MALE', 'bob.builder@email.com', '0722222223', '203 Patient St', 'History of asthma.', NOW(), NOW()),
('patient.three', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Charlie', 'Chaplin', '1995-04-01', 'MALE', 'charlie.chaplin@email.com', '0722222224', '204 Patient St', NULL, NOW(), NOW());

-- Create Default Appointments
 -- NOTE: We assume the IDs for doctors and patients will be 1, 2, 3 based on the insertion order.
 INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, appointment_type, symptoms, status, created_at, updated_at)
 VALUES
 (1, 1, CURDATE() + INTERVAL 3 DAY, '10:00:00', 'CONSULTATION', 'Chest pain and shortness of breath.', 'CONFIRMED', NOW(), NOW()),
 (2, 2, CURDATE() + INTERVAL 5 DAY, '14:00:00', 'CONSULTATION', 'Frequent headaches and dizziness.', 'PENDING', NOW(), NOW()),
 (3, 3, CURDATE() + INTERVAL 1 WEEK, '09:00:00', 'ROUTINE_CHECKUP', 'Annual checkup for my child.', 'CONFIRMED', NOW(), NOW()),
 (1, 2, CURDATE() + INTERVAL 10 DAY, '11:00:00', 'FOLLOW_UP', 'Follow-up on previous consultation.', 'PENDING', NOW(), NOW());