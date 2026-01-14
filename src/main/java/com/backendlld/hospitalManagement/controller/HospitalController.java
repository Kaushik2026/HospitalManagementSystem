package com.backendlld.hospitalManagement.controller;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import com.backendlld.hospitalManagement.model.Insurance;
import com.backendlld.hospitalManagement.service.DoctorService;
import com.backendlld.hospitalManagement.service.InsuranceService;
import com.backendlld.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final InsuranceService insuranceService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping("/insurance/{patientId}")
    public ResponseEntity<PatientResponseDto> allotInsuranceToPatient(@PathVariable Long patientId) {
         return ResponseEntity.ok(insuranceService.allotInsuranceToPatient(patientId,new Insurance()));
    }
    @DeleteMapping("/insurance/{patientId}")
    public ResponseEntity<PatientResponseDto> disassociateInsuranceFromPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(insuranceService.disassociateInsuranceFromPatient(patientId));
    }
}