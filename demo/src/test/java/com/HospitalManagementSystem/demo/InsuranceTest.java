package com.HospitalManagementSystem.demo;


import com.HospitalManagementSystem.demo.entity.Insurance;
import com.HospitalManagementSystem.demo.entity.Patient;
import com.HospitalManagementSystem.demo.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

//
//    @Autowired
//    private InsuranceService insuranceService;
//
//    @Test
//    public void testInsurance(){
//        Insurance insurance= Insurance.builder()
//                .policyNumber("SIL_123")
//                .provider("SIL")
//                .validUntil(LocalDate.of(2030, 12, 30))
//                .build();
//
//        Patient patient= insuranceService.assignInsuranceToPatient(insurance, 1L);
//        System.out.println(patient);
//
//
//       var newPatient= insuranceService.dissociateInsuranceFromPatient(patient.getId());
//        System.out.println(newPatient);
//    }
}
