package com.HospitalManagementSystem.demo.dto.appointmentDto;

import com.HospitalManagementSystem.demo.dto.doctorDto.DoctorResponseDto;
import com.HospitalManagementSystem.demo.dto.patientDto.PatientResponseDto;
import com.HospitalManagementSystem.demo.entity.type.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AppointmentResponseDto {

    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private AppointmentStatus status;
    private DoctorResponseDto doctor;
    private PatientResponseDto patient;

}
