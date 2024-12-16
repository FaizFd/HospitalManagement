package com.patient_service.patientservice.dto;

import lombok.Data;

@Data
public class PatientDTO {
    private Long id;
    private String name;
    private Integer age;
    private String medicalCondition;
    // Additional fields as needed
}
