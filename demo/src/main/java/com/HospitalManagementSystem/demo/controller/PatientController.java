package com.HospitalManagementSystem.demo.controller;

import com.HospitalManagementSystem.demo.dto.appointmentDto.AppointmentResponseDto;
import com.HospitalManagementSystem.demo.dto.patientDto.PatientProfileUpdateDto;
import com.HospitalManagementSystem.demo.dto.patientDto.PatientResponseDto;
import com.HospitalManagementSystem.demo.security.AuthUtil;
import com.HospitalManagementSystem.demo.service.AppointmentService;
import com.HospitalManagementSystem.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PatientController{

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final AuthUtil authUtil;


    @GetMapping("/patient/profile/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientProfile(@PathVariable Long patientId){
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @GetMapping("/appointment/{patientId}")
    public ResponseEntity<List<AppointmentResponseDto>> getPatientAppointment(@PathVariable Long patientId){
        return ResponseEntity.ok(appointmentService.getAllAppointmentOfPatient(patientId));
    }

    @PatchMapping("/patient/profile/update")
    public ResponseEntity<PatientResponseDto> updatePatientProfile( @RequestBody PatientProfileUpdateDto patientProfileUpdateDto){
        Long userId = authUtil.getCurrentUserId();
        return ResponseEntity.ok(patientService.updatePatientProfile(userId, patientProfileUpdateDto));
    }

}
