package com.patient_service.patientservice.controller;
import com.patient_service.patientservice.dto.PatientDTO;
import com.patient_service.patientservice.entity.Patient;
import com.patient_service.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public PatientDTO createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @GetMapping("/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/{id}")
    public PatientDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO) {
        return patientService.updatePatient(id, patientDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @GetMapping
    public List<PatientDTO> searchPatients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String condition) {
        return patientService.searchPatients(name, age, condition);
    }

    @GetMapping("/{id}/history")
    public List<String> getPatientHistory(@PathVariable Long id) {
        // Could return a list of treatments or MedicalRecordDTO
        return patientService.getPatientHistoryDescriptions(id);
    }
}
