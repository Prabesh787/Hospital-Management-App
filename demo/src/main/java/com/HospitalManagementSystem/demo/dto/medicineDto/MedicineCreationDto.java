package com.HospitalManagementSystem.demo.dto.medicineDto;

import lombok.Data;

@Data
public class MedicineCreationDto {

    private String name;
    private String manufacturer;
    private String usage;
    private String strength;
    private String form;

}
