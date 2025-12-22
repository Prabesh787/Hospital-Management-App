package com.HospitalManagementSystem.demo.entity.joinEntity;

import com.HospitalManagementSystem.demo.entity.transactionalEntity.Prescription;
import com.HospitalManagementSystem.demo.entity.masterEntity.Medicine;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_medicines")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrescriptionMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MANY prescription medicines belong to ONE prescription
    @ManyToOne(optional = false)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    // MANY prescription medicines can refer to ONE medicine
    @ManyToOne(optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    private Integer quantity;
    private String dosage;
    private String frequency;
    private Integer duration; // days
    private String notes;
}

