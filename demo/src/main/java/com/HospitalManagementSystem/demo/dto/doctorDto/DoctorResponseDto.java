package com.HospitalManagementSystem.demo.dto.doctorDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDto implements Serializable {

    private Long id;
    private String name;
    private String specialization;
    private String email;
}
