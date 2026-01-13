package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.dtos.OnboardDoctorRequestDto;

import java.util.List;

public interface DoctorService {
    List<DoctorResponseDto> getAllDoctors();
    DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onBoardDoctorRequestDto);
}
