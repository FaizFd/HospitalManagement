package com.doctor_service.doctorservice.service;

import com.doctor_service.doctorservice.dto.DoctorDTO;
import com.doctor_service.doctorservice.entity.Doctor;
import com.doctor_service.doctorservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Adjust the URL of the Patient Service based on your setup
    private static final String PATIENT_SERVICE_URL = "http://localhost:8081/patients?doctorId=";

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor = doctorRepository.save(doctor);
        doctorDTO.setId(doctor.getId());
        return doctorDTO;
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return toDTO(doctor);
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor = doctorRepository.save(doctor);
        return toDTO(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<DoctorDTO> searchDoctors(String name, String specialty) {
        List<Doctor> result;
        if (name != null) {
            result = doctorRepository.findByNameContainingIgnoreCase(name);
        } else if (specialty != null) {
            result = doctorRepository.findBySpecialtyContainingIgnoreCase(specialty);
        } else {
            result = doctorRepository.findAll();
        }
        return result.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Example of retrieving patients assigned to a specific doctor
    public List<Object> getPatientsForDoctor(Long doctorId) {
        // Assuming Patient Service returns a list of patient DTOs
        Object[] patients = restTemplate.getForObject(PATIENT_SERVICE_URL + doctorId, Object[].class);
        if (patients != null) {
            return List.of(patients);
        } else {
            return List.of();
        }
    }

    private DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialty(doctor.getSpecialty());
        return dto;
    }
}
