package com.HospitalManagementSystem.demo.service;

import com.HospitalManagementSystem.demo.dto.medicineDto.MedicineCreationDto;
import com.HospitalManagementSystem.demo.dto.medicineDto.MedicineResponseDto;
import com.HospitalManagementSystem.demo.entity.masterEntity.Medicine;
import com.HospitalManagementSystem.demo.repository.MedicineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MedicineResponseDto addMedicine(MedicineCreationDto medicineCreationDto) {

        if (medicineRepository.existsByName(medicineCreationDto.getName())) {
            throw new BadCredentialsException(
                    "Medicine with name '%s' already exists".formatted(medicineCreationDto.getName())
            );
        }
            Medicine medicine = Medicine.builder()
                    .name(medicineCreationDto.getName())
                    .manufacturer(medicineCreationDto.getManufacturer())
                    .usage(medicineCreationDto.getUsage())
                    .strength(medicineCreationDto.getStrength())
                    .form(medicineCreationDto.getForm())
                    .build();

            medicineRepository.save(medicine);

            return modelMapper.map(medicine, MedicineResponseDto.class);
    }

    public List<MedicineResponseDto> getAllMedicine(){

        return medicineRepository.findAll()
                .stream()
                .map(medicine -> modelMapper.map(medicine, MedicineResponseDto.class))
                .collect(Collectors.toList());
    }


    public MedicineResponseDto getMedicineById(Long id) {
       Medicine medicine = medicineRepository.findById(id).orElseThrow();
        return modelMapper.map(medicine, MedicineResponseDto.class);
    }
}
