package com.HospitalManagementSystem.demo;


import com.HospitalManagementSystem.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentTest {


    @Autowired
    AppointmentService appointmentService;

//    @Test
//    public void testAppointment(){
//        Appointment appointment= Appointment.builder()
//                .appointmentTime(LocalDateTime.of(2025,12,20, 14, 15, 0))
//                .reason("Normal check up")
//                .build();
//
//        Appointment newAppointment = appointmentService.createNewAppointment(appointment, 2L, 3L);
//        System.out.println(newAppointment);
//
//        var updatedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(), 3L);
//        System.out.println(updatedAppointment);
//    }


}
