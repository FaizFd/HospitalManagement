package com.doctor_service.doctorservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorScheduleDTO {
    private Long id;
    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean available;
}
