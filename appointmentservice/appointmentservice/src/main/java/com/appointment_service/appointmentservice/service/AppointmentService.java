package com.appointment_service.appointmentservice.service;

import com.appointment_service.appointmentservice.dto.AppointmentDTO;
import com.appointment_service.appointmentservice.entity.Appointment;
import com.appointment_service.appointmentservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RestTemplate restTemplate;

    // URLs for other services (adjust as needed)
    private static final String PATIENT_SERVICE_URL = "http://localhost:8081/patients/";
    private static final String DOCTOR_SERVICE_URL = "http://localhost:8082/doctors/";

    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        // Validate patient
        if (!isPatientExists(dto.getPatientId())) {
            throw new RuntimeException("Patient not found");
        }
        // Validate doctor
        if (!isDoctorExists(dto.getDoctorId())) {
            throw new RuntimeException("Doctor not found");
        }
        // Additional availability checks could be added here if needed.

        Appointment appointment = toEntity(dto);
        appointment.setStatus("scheduled");
        appointment = appointmentRepository.save(appointment);
        return toDTO(appointment);
    }

    public AppointmentDTO rescheduleAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        // Check if appointment is not cancelled or completed before rescheduling
        if (!"scheduled".equalsIgnoreCase(appointment.getStatus())) {
            throw new RuntimeException("Cannot reschedule a non-scheduled appointment");
        }
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment = appointmentRepository.save(appointment);
        return toDTO(appointment);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (!"scheduled".equalsIgnoreCase(appointment.getStatus())) {
            throw new RuntimeException("Appointment is not in a cancellable state");
        }
        appointment.setStatus("cancelled");
        appointmentRepository.save(appointment);
    }

    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return toDTO(appointment);
    }

    public List<AppointmentDTO> searchAppointments(Long patientId, Long doctorId, LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .filter(a -> (patientId == null || a.getPatientId().equals(patientId)))
                .filter(a -> (doctorId == null || a.getDoctorId().equals(doctorId)))
                .filter(a -> {
                    if (date == null) return true;
                    return a.getAppointmentTime().toLocalDate().equals(date);
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isPatientExists(Long patientId) {
        try {
            restTemplate.getForObject(PATIENT_SERVICE_URL + patientId, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDoctorExists(Long doctorId) {
        try {
            restTemplate.getForObject(DOCTOR_SERVICE_URL + doctorId, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Appointment toEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setPatientId(dto.getPatientId());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(dto.getStatus());
        return appointment;
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatientId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }
}
