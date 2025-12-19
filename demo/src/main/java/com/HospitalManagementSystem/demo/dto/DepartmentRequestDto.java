package com.HospitalManagementSystem.demo.dto;


import lombok.Data;


@Data
public class DepartmentRequestDto {

    private String name;

    private Long headDoctorId;
}
