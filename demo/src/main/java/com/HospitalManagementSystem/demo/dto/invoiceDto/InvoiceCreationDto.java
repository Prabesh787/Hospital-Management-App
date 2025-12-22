package com.HospitalManagementSystem.demo.dto.invoiceDto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCreationDto {

    private Long appointmentId;  // Optional: create from appointment
    private LocalDateTime dueDate;
    private BigDecimal discount;
    private BigDecimal labCharges;
    private BigDecimal otherCharges;
    private BigDecimal taxAmount;
    private String notes;
}
