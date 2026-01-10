package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;

import java.util.List;

public interface DoctorService {
    List<DoctorResponseDto> getAllDoctors();
}
