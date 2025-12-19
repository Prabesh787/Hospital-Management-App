package com.HospitalManagementSystem.demo.controller;


import com.HospitalManagementSystem.demo.dto.AppointmentResponseDto;
import com.HospitalManagementSystem.demo.dto.CreateAppointmentRequestDto;
import com.HospitalManagementSystem.demo.dto.DoctorResponseDto;
import com.HospitalManagementSystem.demo.dto.ReAssignDoctorRequestDto;
import com.HospitalManagementSystem.demo.entity.Appointment;
import com.HospitalManagementSystem.demo.security.AuthUtil;
import com.HospitalManagementSystem.demo.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AuthUtil authUtil;

    @GetMapping("/public/getAllAppointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto){
        Long userId = authUtil.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto, userId));
    }

    @PostMapping("/reAssignAppointment")
    public ResponseEntity<AppointmentResponseDto> reAssignAppointment(@RequestBody ReAssignDoctorRequestDto reAssignDoctorRequestDto){
        return ResponseEntity.ok(appointmentService.reAssignAppointmentToAnotherDoctor(reAssignDoctorRequestDto.getAppointmentId(), reAssignDoctorRequestDto.getDoctorId()));
    }
}
