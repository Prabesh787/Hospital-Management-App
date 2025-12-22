package com.HospitalManagementSystem.demo.controller;


import com.HospitalManagementSystem.demo.dto.medicineDto.MedicineCreationDto;
import com.HospitalManagementSystem.demo.dto.medicineDto.MedicineResponseDto;
import com.HospitalManagementSystem.demo.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<MedicineResponseDto> addMedicine(@RequestBody MedicineCreationDto medicineCreationDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("message","Medicine added successfully")
                .body(medicineService.addMedicine(medicineCreationDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicineResponseDto>> getAllMedicine(){
        return ResponseEntity
                .ok(medicineService.getAllMedicine());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> getMedicineById(@PathVariable Long id){
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }
}
