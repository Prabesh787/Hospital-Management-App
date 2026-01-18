package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.masterEntity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

//    @Query("select (count(d) > 0) from Department d")
    boolean existsByName(String name);

    // Or use a query to handle trimming at DB level
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Department d WHERE TRIM(d.name) = TRIM(:name)")
    boolean existsByNameIgnoringSpaces(@Param("name") String name);
}