package com.appointment_service.appointmentservice.repository;

import com.appointment_service.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);

    // You can also write custom queries if needed.
    // For searching by patientId/doctorId/date, you might implement a service-level filter.
}
