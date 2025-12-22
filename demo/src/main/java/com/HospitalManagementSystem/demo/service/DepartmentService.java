package com.HospitalManagementSystem.demo.service;


import com.HospitalManagementSystem.demo.dto.departmentDto.DepartmentRequestDto;
import com.HospitalManagementSystem.demo.dto.departmentDto.DepartmentResponseDto;
import com.HospitalManagementSystem.demo.entity.masterEntity.Department;
import com.HospitalManagementSystem.demo.entity.masterEntity.Doctor;
import com.HospitalManagementSystem.demo.repository.DepartmentRepository;
import com.HospitalManagementSystem.demo.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public DepartmentResponseDto createNewDepartment(DepartmentRequestDto requestDto) {

        // Step 1: Validate department name uniqueness
        if (departmentRepository.existsByName(requestDto.getName())) {
            throw new BadCredentialsException(
                    "Department with name '%s' already exists".formatted(requestDto.getName())
            );
        }

        // Step 2: Create new department entity
        Department department = new Department();
        department.setName(requestDto.getName());
        department.setDoctors(new HashSet<>()); // Initialize empty doctor set

        // Step 3: Handle head doctor assignment (if provided)
        if (requestDto.getHeadDoctorId() != null) {

            // Fetch the doctor from database
            Doctor headDoctor = doctorRepository.findById(requestDto.getHeadDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Doctor not found with id: %d".formatted(requestDto.getHeadDoctorId())
                    ));

            // Set the head doctor
            department.setHeadDoctor(headDoctor);

            // Business Rule: Head doctor is automatically added to department's doctor list
            department.getDoctors().add(headDoctor);
        }

        // Step 4: Save the department
        Department savedDepartment = departmentRepository.save(department);

        // Step 5: Map entity to response DTO and return
        return modelMapper.map(savedDepartment, DepartmentResponseDto.class);
    }

//    public DepartmentResponseDto getDepartmentById(Long id) {
//    }
//
//    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto departmentRequestDto) {
//    }
//
//    public void deleteDepartment(Long id) {
//    }
//
//    public DepartmentResponseDto removeDoctorFromDepartment(Long departmentId, Long doctorId) {
//    }
}
