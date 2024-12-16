package com.doctor_service.doctorservice.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialty;
    // Additional fields as needed
}
