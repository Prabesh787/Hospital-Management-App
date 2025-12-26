package com.HospitalManagementSystem.demo.dto.patientDto;

import com.HospitalManagementSystem.demo.entity.type.BloodGroupType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PatientResponseDto implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
    private Long insurance_id;
    private String profileImageUrl;
}
