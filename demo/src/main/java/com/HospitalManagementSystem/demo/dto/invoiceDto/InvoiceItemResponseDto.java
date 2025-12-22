package com.HospitalManagementSystem.demo.dto.invoiceDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemResponseDto {

    private Long id;
    private String itemName;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    // Medicine information (if applicable)
    private MedicineSummaryDto medicine;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineSummaryDto {
        private Long id;
        private String name;
        private String manufacturer;
        private String strength;
        private String form;
    }
}