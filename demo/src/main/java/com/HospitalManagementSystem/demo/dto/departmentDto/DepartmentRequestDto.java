package com.HospitalManagementSystem.demo.dto.departmentDto;


import lombok.Data;


@Data
public class DepartmentRequestDto {

    private String name;

    private Long headDoctorId;
}
