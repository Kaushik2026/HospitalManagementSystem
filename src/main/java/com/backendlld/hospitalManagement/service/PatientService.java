package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PatientService {

    PatientResponseDto getPatientById(Long id);

    List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize);
}
