package com.HospitalManagementSystem.demo.entity.masterEntity;


import com.HospitalManagementSystem.demo.entity.transactionalEntity.Appointment;
import com.HospitalManagementSystem.demo.entity.transactionalEntity.Insurance;
import com.HospitalManagementSystem.demo.entity.type.BloodGroupType;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
        name = "patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date", columnList = "birthDate")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {

    @Id
    private Long id; // shared with User (via @MapsId)

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 40)
    private String name;

    private String profileImageUrl;

    private String imagePublicId;

    /* ========= Profile fields (nullable initially) ========= */
    @Column(unique = true)
    private String email;

    private LocalDate birthDate;

    private String gender;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    /* ========= Profile completion flag ========= */

    @Builder.Default
    @Column(nullable = false)
    private Boolean profileCompleted = false;

    /* ========= Relationships ========= */

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    /* ========= Metadata ========= */

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}