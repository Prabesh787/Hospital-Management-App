package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.transactionalEntity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}