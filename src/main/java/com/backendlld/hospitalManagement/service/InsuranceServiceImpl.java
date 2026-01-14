package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.PatientResponseDto;
import com.backendlld.hospitalManagement.model.Insurance;
import com.backendlld.hospitalManagement.model.Patient;
import com.backendlld.hospitalManagement.repository.InsuranceRepository;
import com.backendlld.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService{
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PatientResponseDto allotInsuranceToPatient(Long patientId, Insurance insurance) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(insurance);

        insurance.setPatient(patient);// setting the bidirectional relationship,incase we need this later in current session/transaction.

//        return patientRepository.save(patient);//no need to save here bcoz we have started a transaction,
//        so at the end of the transaction,it will automatically flush all changes to the database bcoz we have dirtied the
//        patient and when transaction ends it will check the changes before commiting and update it in the database.
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    @Override
    @Transactional
    public PatientResponseDto disassociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));
        patient.setInsurance(null);//we are not removing the insurance entity from the database,
        // just disassociating it from the patient.as patient is the owner of the relationship. now patient
        // will not have any insurance associated with it. and it got dirty as well so the remove operation
        // will be cascaded to insurance entity as we have set cascade = ALL in patient entity.
        return modelMapper.map(patient, PatientResponseDto.class);
    }
}
