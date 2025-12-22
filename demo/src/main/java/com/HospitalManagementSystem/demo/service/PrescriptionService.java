package com.HospitalManagementSystem.demo.service;

import com.HospitalManagementSystem.demo.dto.prescriptionDto.PrescriptionRequestDto;
import com.HospitalManagementSystem.demo.dto.prescriptionDto.PrescriptionResponseDto;
import com.HospitalManagementSystem.demo.entity.joinEntity.PrescriptionMedicine;
import com.HospitalManagementSystem.demo.entity.masterEntity.Medicine;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Appointment;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Prescription;
import com.HospitalManagementSystem.demo.repository.AppointmentRepository;
import com.HospitalManagementSystem.demo.repository.MedicineRepository;
import com.HospitalManagementSystem.demo.repository.PrescriptionMedicineRepository;
import com.HospitalManagementSystem.demo.repository.PrescriptionRepository;
import com.HospitalManagementSystem.demo.security.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;
    private final AuthUtil authUtil;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;

    @Transactional
    public PrescriptionResponseDto createPrescription(PrescriptionRequestDto prescriptionRequestDto) {
        // Get logged-in user ID
        Long loggedInUserId = authUtil.getCurrentUserId();

        // Fetch appointment
        Appointment appointment = appointmentRepository.findById(prescriptionRequestDto.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Appointment not found with id: %d".formatted(prescriptionRequestDto.getAppointmentId())
                ));

        // Check if logged-in user is the doctor for this appointment
        if (!appointment.getDoctor().getId().equals(loggedInUserId)) {
            throw new EntityNotFoundException(
                    "You are not authorized to create prescription for this appointment"
            );
        }

        // Check if appointment already has a prescription
        if (appointment.getPrescription() != null) {
            throw new BadCredentialsException(
                    "Prescription already exists for this appointment"
            );
        }

        // Build prescription first (without medicines)
        Prescription prescription = Prescription.builder()
                .diagnosis(prescriptionRequestDto.getDiagnosis())
                .instructions(prescriptionRequestDto.getInstructions())
                .appointment(appointment)
                .build();

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        // Build prescription medicines and set the prescription reference
        List<PrescriptionMedicine> prescriptionMedicines = prescriptionRequestDto.getMedicines()
                .stream()
                .map(medicineDto -> {
                    Medicine medicine = medicineRepository.findById(medicineDto.getMedicineId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Medicine not found with id: %d".formatted(medicineDto.getMedicineId())
                            ));

                    return PrescriptionMedicine.builder()
                            .prescription(prescription)  // SET THE PRESCRIPTION REFERENCE
                            .medicine(medicine)
                            .frequency(medicineDto.getFrequency())
                            .duration(medicineDto.getDuration())
                            .quantity(medicineDto.getQuantity())
                            .notes(medicineDto.getNotes())
                            .build();
                })
                .toList();

// Save prescription first to get the ID
         savedPrescription = prescriptionRepository.save(prescription);

// Update the prescription reference with the saved entity
        Prescription finalSavedPrescription = savedPrescription;
        prescriptionMedicines.forEach(pm -> pm.setPrescription(finalSavedPrescription));

// Save all prescription medicines
        prescriptionMedicineRepository.saveAll(prescriptionMedicines);

// Set the medicines on the prescription entity
        savedPrescription.setMedicines(prescriptionMedicines);


        // Return the saved prescription as response DTO
        return modelMapper.map(savedPrescription, PrescriptionResponseDto.class);
    }

   public PrescriptionResponseDto getPrescriptionOfAppointment(Long appointmentId){
       // Fetch the appointment with prescription
       Appointment appointment = appointmentRepository.findById(appointmentId)
               .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: %d".formatted(appointmentId)));

       // Check if prescription exists
       Prescription prescription = appointment.getPrescription();
       if (prescription == null) {
           throw new EntityNotFoundException("No prescription found for appointment id: %d".formatted(appointmentId));
       }

       return modelMapper.map(prescription, PrescriptionResponseDto.class);

   }


























}