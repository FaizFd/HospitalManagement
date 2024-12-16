package com.patient_service.patientservice.controller;

import com.patient_service.patientservice.dto.MedicalRecordDTO;
import com.patient_service.patientservice.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public MedicalRecordDTO createRecord(@Valid @RequestBody MedicalRecordDTO recordDTO) {
        return medicalRecordService.createMedicalRecord(recordDTO);
    }

    @GetMapping("/{id}")
    public MedicalRecordDTO getRecord(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @PutMapping("/{id}")
    public MedicalRecordDTO updateRecord(@PathVariable Long id, @Valid @RequestBody MedicalRecordDTO recordDTO) {
        return medicalRecordService.updateMedicalRecord(id, recordDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

    @GetMapping("/by-patient/{patientId}")
    public List<MedicalRecordDTO> getRecordsByPatient(@PathVariable Long patientId) {
        return medicalRecordService.getRecordsByPatientId(patientId);
    }
}
