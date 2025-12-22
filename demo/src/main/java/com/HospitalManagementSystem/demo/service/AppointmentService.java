package com.HospitalManagementSystem.demo.service;


import com.HospitalManagementSystem.demo.dto.appointmentDto.AppointmentResponseDto;
import com.HospitalManagementSystem.demo.dto.appointmentDto.CreateAppointmentRequestDto;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Appointment;
import com.HospitalManagementSystem.demo.entity.masterEntity.Doctor;
import com.HospitalManagementSystem.demo.entity.masterEntity.Patient;
import com.HospitalManagementSystem.demo.entity.type.AppointmentStatus;
import com.HospitalManagementSystem.demo.repository.AppointmentRepository;
import com.HospitalManagementSystem.demo.repository.DoctorRepository;
import com.HospitalManagementSystem.demo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Secured("ROLE_PATIENT")
    @PreAuthorize("(hasRole('ROLE_PATIENT') and #patientId == authentication.principal.id)")
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto, Long patientId){
        Long doctorId = createAppointmentRequestDto.getDoctorId();
//        Long patientId = createAppointmentRequestDto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: %d".formatted(patientId)));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: %d".formatted(doctorId)));
        Appointment appointment = Appointment.builder()
                .reason(createAppointmentRequestDto.getReason())
                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
                .appointmentStatus(AppointmentStatus.BOOKED)
                .build();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
//    @PreAuthorize("hasAuthority('appointment:write') or #doctorId == authentication.principal.id")
    public AppointmentResponseDto reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment= appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor= doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);
        doctor.getAppointments().add(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @PreAuthorize("hasAuthority('appointment:write') or #doctorId == authentication.principal.id")
    public List<AppointmentResponseDto> getAllAppointmentOfDoctor(Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }


    public List<AppointmentResponseDto> getAllAppointment(){

        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("(hasRole('ROLE_PATIENT') and #patientId == authentication.principal.id)")
    public List<AppointmentResponseDto> getAllAppointmentOfPatient(Long patientId){
        Patient doctor = patientRepository.findById(patientId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
