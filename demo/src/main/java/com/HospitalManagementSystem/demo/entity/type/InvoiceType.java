package com.HospitalManagementSystem.demo.entity.type;

public enum InvoiceType {
    APPOINTMENT_BASED,    // Created from appointment
    DIRECT_BILLING,       // Walk-in patient, direct services
    EMERGENCY,            // Emergency services
    PHARMACY_ONLY,        // Only medicine purchase
    LAB_ONLY,             // Only lab tests
    PROCEDURE,            // Surgical procedures, treatments
    CUSTOM                // Custom/mixed services
}