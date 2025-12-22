package com.HospitalManagementSystem.demo.dto.doctorDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReAssignDoctorRequestDto {

    Long appointmentId;
    Long doctorId;
}
