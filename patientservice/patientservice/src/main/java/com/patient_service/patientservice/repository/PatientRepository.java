package com.patient_service.patientservice.repository;

import com.patient_service.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainingIgnoreCase(String name);
    List<Patient> findByAge(Integer age);
    List<Patient> findByMedicalConditionContainingIgnoreCase(String condition);
}
