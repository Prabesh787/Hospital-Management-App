package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.transactionalEntity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
