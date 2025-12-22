package com.HospitalManagementSystem.demo.dto.medicineDto;

import lombok.Data;

@Data
public class MedicineResponseDto {

    private Long id;
    private String name;
    private String manufacturer;
    private String usage;
    private String strength;
    private String form;
}
