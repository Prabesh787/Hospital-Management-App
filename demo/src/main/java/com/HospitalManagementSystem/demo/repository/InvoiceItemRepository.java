package com.HospitalManagementSystem.demo.repository;

import com.HospitalManagementSystem.demo.entity.transactionalEntity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}