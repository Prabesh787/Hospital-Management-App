package com.HospitalManagementSystem.demo.repository;


import com.HospitalManagementSystem.demo.entity.masterEntity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    boolean existsByName(String name);
}
