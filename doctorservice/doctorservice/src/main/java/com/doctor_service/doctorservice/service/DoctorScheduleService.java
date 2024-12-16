package com.doctor_service.doctorservice.service;

import com.doctor_service.doctorservice.dto.DoctorScheduleDTO;
import com.doctor_service.doctorservice.entity.DoctorSchedule;
import com.doctor_service.doctorservice.repository.DoctorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    public DoctorScheduleDTO createSchedule(DoctorScheduleDTO dto) {
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctorId(dto.getDoctorId());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setAvailable(dto.getAvailable());
        schedule = doctorScheduleRepository.save(schedule);
        dto.setId(schedule.getId());
        return dto;
    }

    public DoctorScheduleDTO getScheduleById(Long id) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return toDTO(schedule);
    }

    public DoctorScheduleDTO updateSchedule(Long id, DoctorScheduleDTO dto) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        schedule.setDoctorId(dto.getDoctorId());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setAvailable(dto.getAvailable());
        schedule = doctorScheduleRepository.save(schedule);
        return toDTO(schedule);
    }

    public void deleteSchedule(Long id) {
        doctorScheduleRepository.deleteById(id);
    }

    public List<DoctorScheduleDTO> getSchedulesByDoctorId(Long doctorId) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDoctorId(doctorId);
        return schedules.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DoctorScheduleDTO toDTO(DoctorSchedule schedule) {
        DoctorScheduleDTO dto = new DoctorScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDoctorId(schedule.getDoctorId());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        dto.setAvailable(schedule.getAvailable());
        return dto;
    }
}
