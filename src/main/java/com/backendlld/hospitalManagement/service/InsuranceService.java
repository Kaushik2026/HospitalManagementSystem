package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import com.backendlld.hospitalManagement.model.Insurance;
import com.backendlld.hospitalManagement.model.Patient;

public interface InsuranceService {

    PatientResponseDto allotInsuranceToPatient(Long patientId, Insurance insurance);
    PatientResponseDto disassociateInsuranceFromPatient(Long patientId);
}
