package com.HospitalManagementSystem.demo.controller;


import com.HospitalManagementSystem.demo.dto.prescriptionDto.PrescriptionRequestDto;
import com.HospitalManagementSystem.demo.dto.prescriptionDto.PrescriptionResponseDto;
import com.HospitalManagementSystem.demo.repository.PrescriptionRepository;
import com.HospitalManagementSystem.demo.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;


    @PostMapping("/new")
    public ResponseEntity<PrescriptionResponseDto> createPrescription(@RequestBody PrescriptionRequestDto prescriptionRequestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("message","Prescription created successfully")
                .body(prescriptionService.createPrescription(prescriptionRequestDto));

    }

    @PostMapping("/getPrescription/{appointmentId}")
    public ResponseEntity<PrescriptionResponseDto> getPrescriptionOfAppointment(@PathVariable Long appointmentId){
        return ResponseEntity
                .ok(prescriptionService.getPrescriptionOfAppointment(appointmentId));
    }
}
