package com.HospitalManagementSystem.demo.dto.prescriptionDto;

import com.HospitalManagementSystem.demo.dto.medicineDto.MedicineResponseDto;
import lombok.Data;
import java.util.List;

@Data
public class PrescriptionResponseDto {

    private Long id;
    private String diagnosis;
    private List<PrescriptionMedicineResponseDto> medicines;
    private String instructions;

    @Data
    public static class PrescriptionMedicineResponseDto {
        private Long id;
        private MedicineResponseDto medicine;
        private String dosage;
        private String frequency;
        private Integer duration; // in days
        private String notes;
    }
}
