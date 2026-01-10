package com.backendlld.hospitalManagement;

import com.backendlld.hospitalManagement.model.Appointment;
import com.backendlld.hospitalManagement.model.Insurance;
import com.backendlld.hospitalManagement.model.Patient;
import com.backendlld.hospitalManagement.service.AppointmentService;
import com.backendlld.hospitalManagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsuranceTest {
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private AppointmentService appointmentService;

    @Test
    void insuranceTest(){
//        Insurance insurance = Insurance.builder()
//                .providerName("HealthSecure")
//                .policyNumber("HS-987654321")
//                .validTill(java.time.LocalDate.of(2026, 12, 31))
//                .build();
//
//        Patient patient=insuranceService.allotInsuranceToPatient(1L, insurance);

        insuranceService.disassociateInsuranceFromPatient(1L);
//        System.out.println(patient);
    }

//    @Test
//    void createNewAppointmentTest(){
//        var appointment = Appointment.builder()
//                .appointmentDateTime(java.time.LocalDateTime.of(2024, 7, 15, 10, 30))
//                .reasonForVisit("Regular Checkup")
//                .build();
//
//        var savedAppointment = appointmentService.createNewAppointment(appointment, 1L, 1L);
//        System.out.println(savedAppointment);
//    }

//    @Test
//    void reAssignAppointmentToAnotherDoctorTest(){
//        var updatedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(3L, 2L);
//        System.out.println(updatedAppointment);
//    }
}
