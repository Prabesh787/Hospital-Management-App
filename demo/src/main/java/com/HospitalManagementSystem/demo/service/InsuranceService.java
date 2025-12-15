package com.HospitalManagementSystem.demo.service;


import com.HospitalManagementSystem.demo.entity.Appointment;
import com.HospitalManagementSystem.demo.entity.Insurance;
import com.HospitalManagementSystem.demo.entity.Patient;
import com.HospitalManagementSystem.demo.repository.DoctorRepository;
import com.HospitalManagementSystem.demo.repository.InsuranceRepository;
import com.HospitalManagementSystem.demo.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient= patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: %d".formatted(patientId)));

        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        return patient;

    }

    @Transactional
    public Patient dissociateInsuranceFromPatient(Long patientId){
        Patient patient= patientRepository.findById(patientId)
                .orElseThrow();

        patient.setInsurance(null);
        return patient;
    }

}
