package com.HospitalManagementSystem.demo.dto.doctorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnboardDoctorRequestDto {
    private Long userId;
    private String specialization;
    private String name;
}