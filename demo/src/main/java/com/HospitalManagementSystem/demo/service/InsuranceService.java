package com.HospitalManagementSystem.demo.service;


import com.HospitalManagementSystem.demo.dto.patientDto.PatientResponseDto;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Insurance;
import com.HospitalManagementSystem.demo.entity.masterEntity.Patient;
import com.HospitalManagementSystem.demo.repository.DoctorRepository;
import com.HospitalManagementSystem.demo.repository.InsuranceRepository;
import com.HospitalManagementSystem.demo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PatientResponseDto assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient= patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: %d".formatted(patientId)));

        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        return modelMapper.map(patient, PatientResponseDto.class);

    }

    @Transactional
    public Patient dissociateInsuranceFromPatient(Long patientId){
        Patient patient= patientRepository.findById(patientId)
                .orElseThrow();

        patient.setInsurance(null);
        return patient;
    }

}
