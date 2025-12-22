package com.HospitalManagementSystem.demo.dto.prescriptionDto;

import lombok.Data;
import java.util.List;

@Data
public class PrescriptionRequestDto {

    private String diagnosis;
    private List<PrescriptionMedicineDto> medicines;
    private String instructions;
    private Long appointmentId;

    @Data
    public static class PrescriptionMedicineDto {
        private Long medicineId;
        private String dosage;
        private Integer quantity;
        private String frequency;
        private Integer duration; // in days
        private String notes;
    }
}