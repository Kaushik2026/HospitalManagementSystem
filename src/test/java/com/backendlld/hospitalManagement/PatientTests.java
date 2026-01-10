package com.backendlld.hospitalManagement;

import com.backendlld.hospitalManagement.model.Patient;
import com.backendlld.hospitalManagement.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTests {
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatientRepository() {
//        List<Patient> patientList =  patientRepository.findAll();//will create N+1 query issue
        List<Patient> patientList =  patientRepository.findAllPatientWithAppointment();
        System.out.println(patientList);
    }
}
