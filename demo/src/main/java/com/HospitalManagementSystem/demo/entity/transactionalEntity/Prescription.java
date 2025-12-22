package com.HospitalManagementSystem.demo.entity.transactionalEntity;

import com.HospitalManagementSystem.demo.entity.joinEntity.PrescriptionMedicine;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "prescription_id")  // Prescription owns the relationship
    private List<PrescriptionMedicine> medicines;

    private String instructions;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    @ToString.Exclude
    private Appointment appointment;
}