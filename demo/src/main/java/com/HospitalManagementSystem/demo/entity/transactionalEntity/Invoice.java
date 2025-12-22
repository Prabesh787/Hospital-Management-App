package com.HospitalManagementSystem.demo.entity.transactionalEntity;

import com.HospitalManagementSystem.demo.entity.masterEntity.Doctor;
import com.HospitalManagementSystem.demo.entity.masterEntity.Patient;
import com.HospitalManagementSystem.demo.entity.type.InvoiceStatus;
import com.HospitalManagementSystem.demo.entity.type.InvoiceType;
import com.HospitalManagementSystem.demo.entity.type.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @ToString.Exclude
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "primary_doctor_id")  // Optional: for walk-in cases
    private Doctor primaryDoctor;

    @Column(nullable = false)
    private LocalDateTime invoiceDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal medicineCharges;

    @Column(precision = 10, scale = 2)
    private BigDecimal labCharges;

    @Column(precision = 10, scale = 2)
    private BigDecimal otherCharges;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal paidAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal balanceAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Column
    private LocalDateTime paymentDate;

    @Column(length = 500)
    private String notes;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper method to calculate totals
    public void calculateTotals() {
        this.subtotal = consultationFee
                .add(medicineCharges)
                .add(labCharges != null ? labCharges : BigDecimal.ZERO)
                .add(otherCharges != null ? otherCharges : BigDecimal.ZERO);

        BigDecimal discountAmount = discount != null ? discount : BigDecimal.ZERO;
        BigDecimal afterDiscount = subtotal.subtract(discountAmount);

        this.totalAmount = afterDiscount.add(taxAmount != null ? taxAmount : BigDecimal.ZERO);
        this.balanceAmount = totalAmount.subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO);
    }
}