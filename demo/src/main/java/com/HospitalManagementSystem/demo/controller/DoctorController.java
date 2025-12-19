package com.HospitalManagementSystem.demo.controller;

import com.HospitalManagementSystem.demo.dto.AppointmentResponseDto;
import com.HospitalManagementSystem.demo.dto.DoctorResponseDto;
import com.HospitalManagementSystem.demo.entity.User;
import com.HospitalManagementSystem.demo.service.AppointmentService;
import com.HospitalManagementSystem.demo.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @GetMapping("public/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentOfDoctor(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(appointmentService.getAllAppointmentOfDoctor(user.getId()));
    }
}
