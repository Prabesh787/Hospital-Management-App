package com.HospitalManagementSystem.demo.controller;


import com.HospitalManagementSystem.demo.dto.invoiceDto.InvoiceCreationDto;
import com.HospitalManagementSystem.demo.dto.invoiceDto.InvoiceResponseDto;
import com.HospitalManagementSystem.demo.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceResponseDto> createInvoiceFromAppointment(@RequestBody InvoiceCreationDto invoiceCreationDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("message","New Invoice created Successfully")
                .body(invoiceService.createInvoiceFromAppointment(invoiceCreationDto));
    }
}
