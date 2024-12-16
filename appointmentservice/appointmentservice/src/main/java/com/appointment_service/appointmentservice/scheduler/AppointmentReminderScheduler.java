package com.appointment_service.appointmentservice.scheduler;

import com.appointment_service.appointmentservice.dto.DoctorDTO;
import com.appointment_service.appointmentservice.dto.PatientDTO;
import com.appointment_service.appointmentservice.entity.Appointment;
import com.appointment_service.appointmentservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentReminderScheduler {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Adjust these URLs if needed
    private static final String PATIENT_SERVICE_URL = "http://localhost:8081/patients/";
    private static final String DOCTOR_SERVICE_URL = "http://localhost:8082/doctors/";

    // Run every minute for demonstration (adjust to your cron expression)
    @Scheduled(cron = "0 * * * * ?")
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfTomorrow = now.plusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1).minusSeconds(1);

        List<Appointment> tomorrowAppointments = appointmentRepository.findByAppointmentTimeBetween(
                startOfTomorrow, endOfTomorrow
        );

        tomorrowAppointments.forEach(a -> {
            PatientDTO patient = getPatient(a.getPatientId());
            DoctorDTO doctor = getDoctor(a.getDoctorId());
            System.out.println("Reminder: Appointment tomorrow for Patient "
                    + (patient != null ? patient.getId() + " "  + patient.getName() : "Unknown Patient")
                    + " with Doctor "
                    + (doctor != null ? doctor.getId() + " " + doctor.getName() : "Unknown Doctor")
                    + " at " + a.getAppointmentTime());
        });
    }

    private PatientDTO getPatient(Long patientId) {
        try {
            return restTemplate.getForObject(PATIENT_SERVICE_URL + patientId, PatientDTO.class);
        } catch (Exception e) {
            // Handle exceptions (log, fallback, etc.)
            return null;
        }
    }

    private DoctorDTO getDoctor(Long doctorId) {
        try {
            return restTemplate.getForObject(DOCTOR_SERVICE_URL + doctorId, DoctorDTO.class);
        } catch (Exception e) {
            // Handle exceptions (log, fallback, etc.)
            return null;
        }
    }
}
