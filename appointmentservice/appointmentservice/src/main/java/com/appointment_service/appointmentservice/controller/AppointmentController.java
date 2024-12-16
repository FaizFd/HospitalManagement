package com.appointment_service.appointmentservice.controller;

import com.appointment_service.appointmentservice.dto.AppointmentDTO;
import com.appointment_service.appointmentservice.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public AppointmentDTO bookAppointment(@Valid @RequestBody AppointmentDTO dto) {
        return appointmentService.bookAppointment(dto);
    }

    @PutMapping("/{id}")
    public AppointmentDTO rescheduleAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentDTO dto) {
        return appointmentService.rescheduleAppointment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
    }

    @GetMapping("/{id}")
    public AppointmentDTO getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping
    public List<AppointmentDTO> listAppointments(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.searchAppointments(patientId, doctorId, date);
    }
}
