package com.doctor_service.doctorservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean available;
}
