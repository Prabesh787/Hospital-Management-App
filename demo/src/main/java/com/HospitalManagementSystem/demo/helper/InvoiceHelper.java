package com.HospitalManagementSystem.demo.helper;


import com.HospitalManagementSystem.demo.entity.masterEntity.Doctor;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Invoice;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.InvoiceItem;
import com.HospitalManagementSystem.demo.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Helper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InvoiceHelper {

    private final InvoiceRepository invoiceRepository;

    public String generateInvoiceNumber() {
        long count = invoiceRepository.count();
        return String.format("INV-%d-%05d",
                LocalDateTime.now().getYear(),
                count + 1);
    }

    public InvoiceItem createConsultationFeeItem(Invoice invoice, Doctor doctor) {

        BigDecimal fee = BigDecimal.valueOf(
                doctor.getFeePerSession() != null ? doctor.getFeePerSession() : 0
        );

        return InvoiceItem.builder()
                .invoice(invoice)
                .medicine(null) // ❌ No medicine
                .itemName("Doctor Consultation Fee")
                .description(
                        "Consultation with Dr. %s (%s)".formatted(doctor.getName(), doctor.getSpecialization())
                )
                .quantity(1)
                .unitPrice(fee)
                .build(); // totalPrice auto-calculated
    }

}
