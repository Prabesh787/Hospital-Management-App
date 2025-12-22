package com.HospitalManagementSystem.demo.dto.invoiceDto;

import com.HospitalManagementSystem.demo.entity.masterEntity.Patient;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Appointment;
import com.HospitalManagementSystem.demo.entity.type.InvoiceStatus;
import com.HospitalManagementSystem.demo.entity.type.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponseDto {

    private Long id;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private LocalDateTime dueDate;

    // Patient information
    private Long patientId;

    // Appointment information
    private Long appointmentId;

    private String primaryDoctor;

    // Charge breakdown
    private BigDecimal consultationFee;
    private BigDecimal medicineCharges;
    private BigDecimal labCharges;
    private BigDecimal otherCharges;

    // Financial calculations
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal balanceAmount;

    // Payment information
    private InvoiceStatus status;
//    private PaymentMethod paymentMethod;
//    private LocalDateTime paymentDate;

    private String notes;

    // Line items
    private List<InvoiceItemResponseDto> invoiceItems = new ArrayList<>();

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}