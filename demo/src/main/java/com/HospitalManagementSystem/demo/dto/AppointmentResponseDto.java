package com.HospitalManagementSystem.demo.dto;

import com.HospitalManagementSystem.demo.repository.DoctorRepository;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AppointmentResponseDto {

    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorRepository doctor;
}
