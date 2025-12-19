package com.HospitalManagementSystem.demo.dto;


import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {
    private Long id;
    private String name;
    private DoctorResponseDto headDoctor;
    private Set<DoctorResponseDto> doctors;
}
