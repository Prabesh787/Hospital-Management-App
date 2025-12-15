package com.HospitalManagementSystem.demo.controller;

import com.HospitalManagementSystem.demo.dto.PatientResponseDto;
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

//    @PostMapping("/appointments")
//    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentResponseDto createAppointmentResponseDto){
//        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentResponseDto));
//
//    }

    @GetMapping("/patient/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile(){
        Long patientId = 4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @GetMapping("/admin/patients")
    public List<PatientResponseDto> getAllPatients(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return patientService.getAllPatients(pageNumber, pageSize);
    }

}
