package com.patient_service.patientservice.service;

import com.patient_service.patientservice.dto.MedicalRecordDTO;
import com.patient_service.patientservice.entity.MedicalRecord;
import com.patient_service.patientservice.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO recordDTO) {
        MedicalRecord record = new MedicalRecord();
        record.setPatientId(recordDTO.getPatientId());
        record.setTreatmentDescription(recordDTO.getTreatmentDescription());
        record.setTreatmentDate(recordDTO.getTreatmentDate());
        record = medicalRecordRepository.save(record);
        recordDTO.setId(record.getId());
        return recordDTO;
    }

    public MedicalRecordDTO getMedicalRecordById(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));
        return toDTO(record);
    }

    public MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO recordDTO) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MedicalRecord not found"));
        record.setPatientId(recordDTO.getPatientId());
        record.setTreatmentDescription(recordDTO.getTreatmentDescription());
        record.setTreatmentDate(recordDTO.getTreatmentDate());
        record = medicalRecordRepository.save(record);
        return toDTO(record);
    }

    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public List<MedicalRecordDTO> getRecordsByPatientId(Long patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);
        return records.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private MedicalRecordDTO toDTO(MedicalRecord record) {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setId(record.getId());
        dto.setPatientId(record.getPatientId());
        dto.setTreatmentDescription(record.getTreatmentDescription());
        dto.setTreatmentDate(record.getTreatmentDate());
        return dto;
    }
}
