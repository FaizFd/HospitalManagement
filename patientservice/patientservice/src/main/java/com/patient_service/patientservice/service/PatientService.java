package com.patient_service.patientservice.service;

import com.patient_service.patientservice.dto.PatientDTO;
import com.patient_service.patientservice.entity.MedicalRecord;
import com.patient_service.patientservice.entity.Patient;
import com.patient_service.patientservice.repository.MedicalRecordRepository;
import com.patient_service.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setAge(patientDTO.getAge());
        patient.setMedicalCondition(patientDTO.getMedicalCondition());
        patient = patientRepository.save(patient);
        patientDTO.setId(patient.getId());
        return patientDTO;
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return toDTO(patient);
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setName(patientDTO.getName());
        patient.setAge(patientDTO.getAge());
        patient.setMedicalCondition(patientDTO.getMedicalCondition());
        patient = patientRepository.save(patient);
        return toDTO(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientDTO> searchPatients(String name, Integer age, String condition) {
        List<Patient> result;

        if (name != null) {
            result = patientRepository.findByNameContainingIgnoreCase(name);
        } else if (age != null) {
            result = patientRepository.findByAge(age);
        } else if (condition != null) {
            result = patientRepository.findByMedicalConditionContainingIgnoreCase(condition);
        } else {
            result = patientRepository.findAll();
        }

        return result.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<String> getPatientHistoryDescriptions(Long patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);
        return records.stream()
                .map(MedicalRecord::getTreatmentDescription)
                .collect(Collectors.toList());
    }

    private PatientDTO toDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setAge(patient.getAge());
        dto.setMedicalCondition(patient.getMedicalCondition());
        return dto;
    }
}
