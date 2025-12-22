package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.masterEntity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select (count(d) > 0) from Department d")
    boolean existsByName(String name);
}