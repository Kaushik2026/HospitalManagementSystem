package com.backendlld.hospitalManagement.controller;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.dtos.OnboardDoctorRequestDto;
import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import com.backendlld.hospitalManagement.service.DoctorService;
import com.backendlld.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    private final DoctorService doctorService;

    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }
    @PostMapping("/makeHOD/{doctorId}/{departmentId}")
    public ResponseEntity<DoctorResponseDto> makeHOD(
            @PathVariable Long doctorId,
            @PathVariable Long departmentId){
        return ResponseEntity.ok(doctorService.makeHOD(doctorId,departmentId));
    }
}