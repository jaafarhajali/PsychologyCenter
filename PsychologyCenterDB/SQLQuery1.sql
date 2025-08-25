create database PsychologyCenterDB;
use PsychologyCenterDB;

-- Create patient table
CREATE TABLE patient (
    patient_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    password VARCHAR(100) NOT NULL,
    condition_name VARCHAR(100)
);

-- Create doctor table
CREATE TABLE doctor (
    doctor_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(200),
    speciality VARCHAR(100),
    consultation_fees FLOAT,
    password VARCHAR(100) NOT NULL
);

-- Create admin table
CREATE TABLE admin (
    admin_id INT IDENTITY(1,1) PRIMARY KEY,

    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Create appointment table
CREATE TABLE appointment (
    appointment_id INT IDENTITY(1,1) PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
	
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id)
);



INSERT INTO admin (email, password) VALUES ('admin@gmail.com', 'admin123');

select * from patient;
select * from doctor;
select * from appointment;
select *from admin;