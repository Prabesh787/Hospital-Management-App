package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.joinEntity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
}