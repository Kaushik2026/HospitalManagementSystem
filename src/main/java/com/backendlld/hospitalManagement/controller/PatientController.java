package com.backendlld.hospitalManagement.controller;

import com.backendlld.hospitalManagement.dtos.AppointmentResponseDto;
import com.backendlld.hospitalManagement.dtos.CreateAppointmentRequestDto;
import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import com.backendlld.hospitalManagement.service.AppointmentService;
import com.backendlld.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile/{id}")
    private ResponseEntity<PatientResponseDto> getPatientProfile(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

}