package com.HospitalManagementSystem.demo.service;


import com.HospitalManagementSystem.demo.dto.departmentDto.DepartmentRequestDto;
import com.HospitalManagementSystem.demo.dto.departmentDto.DepartmentResponseDto;
import com.HospitalManagementSystem.demo.dto.doctorDto.DoctorResponseDto;
import com.HospitalManagementSystem.demo.entity.masterEntity.Department;
import com.HospitalManagementSystem.demo.entity.masterEntity.Doctor;
import com.HospitalManagementSystem.demo.repository.DepartmentRepository;
import com.HospitalManagementSystem.demo.repository.DoctorRepository;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public DepartmentResponseDto createNewDepartment(DepartmentRequestDto requestDto) {

        // Step 1: Validate department name uniqueness
        String name = requestDto.getName().trim();
        if (departmentRepository.existsByNameIgnoringSpaces(name)) {
            throw new EntityNotFoundException(
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

    public DepartmentResponseDto getDepartmentById(Long id) {

        Department department=departmentRepository.findById(id).orElseThrow();
        return modelMapper.map(department, DepartmentResponseDto.class);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Department not found with id: %d".formatted(id)
                ));

        departmentRepository.delete(department);
    }

    @Transactional
    public DepartmentResponseDto addDoctorToDepartment(Long departmentId, Long doctorId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Department not found with id: %d".formatted(departmentId)
                ));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Doctor not found with id: %d".formatted(doctorId)
                ));

        // Prevent duplicate assignment
        if (department.getDoctors().contains(doctor)) {
            throw new IllegalStateException("Doctor already belongs to this department");
        }

        department.getDoctors().add(doctor);

        Department savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment, DepartmentResponseDto.class);
    }

    @Transactional
    public DepartmentResponseDto removeDoctorFromDepartment(Long departmentId, Long doctorId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Department not found with id: %d".formatted(departmentId)
                ));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Doctor not found with id: %d".formatted(doctorId)
                ));

        // Check if doctor belongs to department
        if (!department.getDoctors().contains(doctor)) {
            throw new IllegalStateException("Doctor is not assigned to this department");
        }

        // Prevent removing head doctor without reassignment
        if (department.getHeadDoctor() != null &&
                department.getHeadDoctor().getId().equals(doctorId)) {
            throw new IllegalStateException(
                    "Cannot remove the head doctor. Assign a new head first."
            );
        }

        department.getDoctors().remove(doctor);

        Department updatedDepartment = departmentRepository.save(department);

        return modelMapper.map(updatedDepartment, DepartmentResponseDto.class);
    }


    @Transactional
    public DepartmentResponseDto assignNewHeadDoctor(Long departmentId, Long doctorId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Department not found with id: %d".formatted(departmentId)
                ));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Doctor not found with id: %d".formatted(doctorId)
                ));

        // Rule: Doctor must belong to the department
        if (!department.getDoctors().contains(doctor)) {
            throw new IllegalStateException(
                    "Doctor must be assigned to the department before becoming head doctor"
            );
        }

        // Optional: prevent reassigning same head doctor
        if (department.getHeadDoctor() != null &&
                department.getHeadDoctor().getId().equals(doctorId)) {
            throw new IllegalStateException("Doctor is already the head of this department");
        }

        department.setHeadDoctor(doctor);

        Department updatedDepartment = departmentRepository.save(department);

        return modelMapper.map(updatedDepartment, DepartmentResponseDto.class);
    }

    public  List<DepartmentResponseDto> getAllDepartment() {
        return departmentRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DepartmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
