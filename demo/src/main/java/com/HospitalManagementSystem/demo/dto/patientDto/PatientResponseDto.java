package com.HospitalManagementSystem.demo.dto.patientDto;

import com.HospitalManagementSystem.demo.entity.type.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
    private Long insurance_id;
}
