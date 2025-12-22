package com.HospitalManagementSystem.demo.controller;

import com.HospitalManagementSystem.demo.dto.departmentDto.DepartmentRequestDto;
import com.HospitalManagementSystem.demo.dto.departmentDto.DepartmentResponseDto;
import com.HospitalManagementSystem.demo.service.DepartmentService;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createNewDepartment(
            @RequestBody DepartmentRequestDto departmentRequestDto) {

        DepartmentResponseDto department = departmentService.createNewDepartment(departmentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("message","Department created successfully")
                .body(department);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentById(@PathVariable Long id) {
//        DepartmentResponseDto department = departmentService.getDepartmentById(id);
//        return ResponseEntity.ok(ApiResponse.success("Department retrieved successfully", department));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<DepartmentResponseDto>> updateDepartment(
//            @PathVariable Long id,
//            @RequestBody DepartmentRequestDto departmentRequestDto) {
//
//        DepartmentResponseDto department = departmentService.updateDepartment(id, departmentRequestDto);
//        return ResponseEntity.ok(ApiResponse.success("Department updated successfully", department));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
//        departmentService.deleteDepartment(id);
//        return ResponseEntity.ok(ApiResponse.success("Department deleted successfully", null));
//    }
//
//    @PostMapping("/{departmentId}/doctors/{doctorId}")
//    public ResponseEntity<ApiResponse<DepartmentResponseDto>> addDoctorToDepartment(
//            @PathVariable Long departmentId,
//            @PathVariable Long doctorId) {
//
//        DepartmentResponseDto department = departmentService.addDoctorToDepartment(departmentId, doctorId);
//        return ResponseEntity.ok(ApiResponse.success("Doctor added to department successfully", department));
//    }
//
//    @DeleteMapping("/{departmentId}/doctors/{doctorId}")
//    public ResponseEntity<ApiResponse<DepartmentResponseDto>> removeDoctorFromDepartment(
//            @PathVariable Long departmentId,
//            @PathVariable Long doctorId) {
//
//        DepartmentResponseDto department = departmentService.removeDoctorFromDepartment(departmentId, doctorId);
//        return ResponseEntity.ok(ApiResponse.success("Doctor removed from department successfully", department));
//    }
}