package com.HospitalManagementSystem.demo.entity.masterEntity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Medicine{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String manufacturer;

    private String usage;

    private String strength; // e.g., 500mg, 250mg

    private String form; // Tablet, Syrup, Injection

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
}
