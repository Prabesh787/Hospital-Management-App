package com.HospitalManagementSystem.demo.controller;

import com.HospitalManagementSystem.demo.dto.DoctorResponseDto;
import com.HospitalManagementSystem.demo.dto.OnboardDoctorRequestDto;
import com.HospitalManagementSystem.demo.dto.PatientResponseDto;
import com.HospitalManagementSystem.demo.service.DoctorService;
import com.HospitalManagementSystem.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }

    @GetMapping("/patients")
    public List<PatientResponseDto> getAllPatients(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return patientService.getAllPatients(pageNumber, pageSize);
    }
}
