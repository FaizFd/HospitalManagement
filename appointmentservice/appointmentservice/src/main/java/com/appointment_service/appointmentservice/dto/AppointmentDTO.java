package com.appointment_service.appointmentservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private String status; // scheduled, cancelled, completed
    // Additional fields: reason, notes, etc.
}
