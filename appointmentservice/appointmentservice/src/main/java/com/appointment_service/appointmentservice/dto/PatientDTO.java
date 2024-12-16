package com.appointment_service.appointmentservice.dto;

import lombok.Data;

@Data
public class PatientDTO {
    private Long id;
    private String name;
    private Integer age;
    private String medicalCondition;
}
