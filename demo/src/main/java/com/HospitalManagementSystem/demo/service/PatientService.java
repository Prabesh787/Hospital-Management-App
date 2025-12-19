package com.HospitalManagementSystem.demo.service;

import com.HospitalManagementSystem.demo.dto.PatientProfileUpdateDto;
import com.HospitalManagementSystem.demo.dto.PatientResponseDto;
import com.HospitalManagementSystem.demo.entity.Patient;
import com.HospitalManagementSystem.demo.repository.PatientRepository;
import com.HospitalManagementSystem.demo.security.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final AuthUtil authUtil;

    private boolean isProfileComplete(Patient patient) {
        return patient.getEmail() != null &&
                patient.getGender() != null &&
                patient.getBirthDate() !=null &&
                patient.getBloodGroup() !=null;
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN') or (hasRole('ROLE_PATIENT') and #userId == authentication.principal.id)")
    @Transactional
    public PatientResponseDto getPatientById(Long userId) {
        Patient patient = patientRepository.findById(userId).orElseThrow();
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("hasAuthority('patient:write') or (hasRole('ROLE_PATIENT') and #userId == authentication.principal.id)")
    public PatientResponseDto updatePatientProfile(Long userId, PatientProfileUpdateDto patientProfileUpdateDto) {

        Patient patient = patientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Update allowed profile fields
        patient.setBirthDate(patientProfileUpdateDto.getBirthDate());
        patient.setGender(patientProfileUpdateDto.getGender());
        patient.setBloodGroup(patientProfileUpdateDto.getBloodGroup());


        // Compute profile completion
        patient.setProfileCompleted(isProfileComplete(patient));

        patient =patientRepository.save(patient);
        return modelMapper.map(patient, PatientResponseDto.class);
    }



}
