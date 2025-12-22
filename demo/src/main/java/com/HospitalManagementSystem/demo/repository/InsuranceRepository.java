package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.transactionalEntity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}