package com.patient_service.patientservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordDTO {
    private Long id;
    private Long patientId;
    private String treatmentDescription;
    private LocalDate treatmentDate;
}