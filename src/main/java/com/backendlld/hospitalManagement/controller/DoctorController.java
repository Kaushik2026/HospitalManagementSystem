package com.backendlld.hospitalManagement.controller;

import com.backendlld.hospitalManagement.dtos.AppointmentResponseDto;
import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import com.backendlld.hospitalManagement.model.User;
import com.backendlld.hospitalManagement.service.AppointmentService;
import com.backendlld.hospitalManagement.service.DoctorService;
import com.backendlld.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @GetMapping("/profile/{id}")
    private ResponseEntity<PatientResponseDto> getPatientProfile(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // For reassigning appointments
    @GetMapping("/doctors/search")
    public ResponseEntity<List<DoctorResponseDto>> searchDoctorsForReassign(
            @PathVariable String specialty) {
        return ResponseEntity.ok(doctorService.searchDoctorsBySpeciality(specialty));
    }

    @PostMapping("/doctor/appointments/{appointmentId},{newDocId}")
    public ResponseEntity<AppointmentResponseDto> changeDoctorForAppointment(
            @PathVariable Long appointmentId,
            @PathVariable Long newDocId
    ) {
        return ResponseEntity.ok(appointmentService.reAssignAppointmentToAnotherDoctor(appointmentId, newDocId));
    }

}