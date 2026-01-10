package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.model.Insurance;
import com.backendlld.hospitalManagement.model.Patient;

public interface InsuranceService {

    Patient allotInsuranceToPatient(Long patientId, Insurance insurance);
    Patient disassociateInsuranceFromPatient(Long patientId);
}
