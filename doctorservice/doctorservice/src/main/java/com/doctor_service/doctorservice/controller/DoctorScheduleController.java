package com.doctor_service.doctorservice.controller;

import com.doctor_service.doctorservice.dto.DoctorScheduleDTO;
import com.doctor_service.doctorservice.service.DoctorScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor-schedules")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @PostMapping
    public DoctorScheduleDTO createSchedule(@Valid @RequestBody DoctorScheduleDTO scheduleDTO) {
        return doctorScheduleService.createSchedule(scheduleDTO);
    }

    @GetMapping("/{id}")
    public DoctorScheduleDTO getSchedule(@PathVariable Long id) {
        return doctorScheduleService.getScheduleById(id);
    }

    @PutMapping("/{id}")
    public DoctorScheduleDTO updateSchedule(@PathVariable Long id, @Valid @RequestBody DoctorScheduleDTO scheduleDTO) {
        return doctorScheduleService.updateSchedule(id, scheduleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        doctorScheduleService.deleteSchedule(id);
    }

    @GetMapping("/by-doctor/{doctorId}")
    public List<DoctorScheduleDTO> getSchedulesByDoctorId(@PathVariable Long doctorId) {
        return doctorScheduleService.getSchedulesByDoctorId(doctorId);
    }
}
