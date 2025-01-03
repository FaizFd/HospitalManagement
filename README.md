# Hospital Management System

A microservices-based backend application for managing hospital operations, built with **Spring Boot Java**. This system uses **Swagger** for API calls and documentation. The application consists of four interconnected services, each designed to handle specific aspects of hospital management.

---

## Services Overview

### 1. **Patient Service**
- Manages patient details, including personal information and medical history.
- Tracks **Patient Medical Records**, which store past medical records and treatment history.

### 2. **Doctor Service**
- Manages doctor information, including specialties and schedules.
- Stores doctor availability for seamless appointment scheduling.

### 3. **Appointment Service**
- Schedules appointments between doctors and patients.
- Sends reminders to both the doctor and patient **1 day before the appointment**.

### 4. **Billing Service**
- Handles billing for patient treatments.
- Automatically updates the patient's **medical records** when a bill is generated.

---

## Features

- **Microservices Architecture**: Four independent services connected via REST APIs.
- **Swagger Integration**: Provides a user-friendly interface for testing APIs and understanding their functionality.
- **Inter-Service Communication**: Services interact seamlessly to manage patients, doctors, appointments, and billing.
- **Automated Reminders**: Appointment reminders ensure timely notifications for patients and doctors.
- **Centralized Medical Records**: Links billing and appointment data to patient medical records for a comprehensive view.

---

## Technologies Used

- **Backend**: Spring Boot (Java)
- **API Documentation**: Swagger
- **Database**: MySQL
- **Build Tool**: Maven/Gradle
- **Dependency Injection**: Spring Framework

---
