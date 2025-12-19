package com.HospitalManagementSystem.demo.dto;

import com.HospitalManagementSystem.demo.entity.type.BloodGroupType;
import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfileUpdateDto {

    private String email;
    private LocalDate birthDate;
    private String gender;
    private BloodGroupType bloodGroup;

}
