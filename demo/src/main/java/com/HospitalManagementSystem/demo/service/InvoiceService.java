package com.HospitalManagementSystem.demo.service;


import com.HospitalManagementSystem.demo.dto.invoiceDto.InvoiceCreationDto;
import com.HospitalManagementSystem.demo.dto.invoiceDto.InvoiceResponseDto;
import com.HospitalManagementSystem.demo.entity.masterEntity.Doctor;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Appointment;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Invoice;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.InvoiceItem;
import com.HospitalManagementSystem.demo.entity.type.InvoiceStatus;
import com.HospitalManagementSystem.demo.entity.type.InvoiceType;
import com.HospitalManagementSystem.demo.helper.InvoiceHelper;
import com.HospitalManagementSystem.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;
    private final InvoiceHelper invoiceHelper;
    private final InvoiceItemRepository invoiceItemRepository;
//    private final InvoiceType invoiceType;

    // Scenario 1: Create invoice from appointment (existing)
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public InvoiceResponseDto createInvoiceFromAppointment(InvoiceCreationDto dto) {

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        Doctor doctor = appointment.getDoctor();
        BigDecimal consultationFee = BigDecimal.valueOf(
                doctor.getFeePerSession() != null ? doctor.getFeePerSession() : 0
        );

        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceHelper.generateInvoiceNumber())
                .invoiceType(InvoiceType.APPOINTMENT_BASED)
                .patient(appointment.getPatient())
                .appointment(appointment)
                .primaryDoctor(doctor)
                .invoiceDate(LocalDateTime.now())
                .dueDate(dto.getDueDate() != null ? dto.getDueDate() : LocalDateTime.now().plusDays(30))
                .consultationFee(consultationFee)
                .medicineCharges(BigDecimal.ZERO)
                .labCharges(dto.getLabCharges() != null ? dto.getLabCharges() : BigDecimal.ZERO)
                .otherCharges(dto.getOtherCharges() != null ? dto.getOtherCharges() : BigDecimal.ZERO)
                .discount(dto.getDiscount() != null ? dto.getDiscount() : BigDecimal.ZERO)
                .taxAmount(dto.getTaxAmount() != null ? dto.getTaxAmount() : BigDecimal.ZERO)
                .paidAmount(BigDecimal.ZERO)
                .status(InvoiceStatus.DRAFT)
                .notes(dto.getNotes())
                .invoiceItems(new ArrayList<>())
                .build();

//        // Auto-add medicines from prescription if exists
//        if (appointment.getPrescription() != null) {
//            addMedicinesFromPrescription(invoice, appointment.getPrescription());
//        }

        // ✅ ADD CONSULTATION FEE AS INVOICE ITEM
        InvoiceItem consultationItem = invoiceHelper.createConsultationFeeItem(invoice, doctor);
        invoice.getInvoiceItems().add(consultationItem);

        invoice.calculateTotals();
        Invoice savedInvoice = invoiceRepository.save(invoice);

        return modelMapper.map(savedInvoice, InvoiceResponseDto.class);
    }

}
