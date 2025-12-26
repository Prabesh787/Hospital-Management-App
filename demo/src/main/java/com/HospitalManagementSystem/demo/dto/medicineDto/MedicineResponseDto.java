package com.HospitalManagementSystem.demo.dto.medicineDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MedicineResponseDto implements Serializable {

    private Long id;
    private String name;
    private String manufacturer;
    private String usage;
    private String strength;
    private String form;
}
