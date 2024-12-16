package com.doctor_service.doctorservice.controller;

import com.doctor_service.doctorservice.dto.DoctorDTO;
import com.doctor_service.doctorservice.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public DoctorDTO createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        return doctorService.createDoctor(doctorDTO);
    }

    @GetMapping("/{id}")
    public DoctorDTO getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public DoctorDTO updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO doctorDTO) {
        return doctorService.updateDoctor(id, doctorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    @GetMapping
    public List<DoctorDTO> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialty) {
        return doctorService.searchDoctors(name, specialty);
    }

    @GetMapping("/{id}/patients")
    public List<Object> getPatientsForDoctor(@PathVariable Long id) {
        // Assume that the Patient Service has an endpoint: /patients?doctorId={id}
        return doctorService.getPatientsForDoctor(id);
    }
}
