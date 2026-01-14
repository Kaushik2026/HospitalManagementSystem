package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.dtos.OnboardDoctorRequestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface DoctorService {
    List<DoctorResponseDto> getAllDoctors();
    DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onBoardDoctorRequestDto);
    DoctorResponseDto makeHOD(Long doctorId, Long departmentId);

    List<DoctorResponseDto> searchDoctorsBySpeciality(String specialty);
}
