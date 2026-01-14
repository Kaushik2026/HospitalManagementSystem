package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.AppointmentResponseDto;
import com.backendlld.hospitalManagement.dtos.CreateAppointmentRequestDto;
import com.backendlld.hospitalManagement.model.Appointment;
import com.backendlld.hospitalManagement.model.Doctor;
import com.backendlld.hospitalManagement.model.Patient;
import com.backendlld.hospitalManagement.repository.AppointmentRepository;
import com.backendlld.hospitalManagement.repository.DoctorRepository;
import com.backendlld.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + dto.getPatientId()));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + dto.getDoctorId()));
        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(dto.getAppointmentTime());
        appointment.setReasonForVisit(dto.getReason());

        if(appointment.getId() != null){
            throw new IllegalArgumentException("New appointment cannot have an existing ID");
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        return modelMapper.map(appointmentRepository.save(appointment), AppointmentResponseDto.class);
    }

    @Override
    @Transactional
    public AppointmentResponseDto reAssignAppointmentToAnotherDoctor(Long appointmentId, Long newDoctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + appointmentId));
        Doctor newDoctor = doctorRepository.findById(newDoctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + newDoctorId));

        appointment.setDoctor(newDoctor);
        newDoctor.getAppointments().add(appointment);

        return modelMapper.map(appointment,AppointmentResponseDto.class);

    }

    @Override
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }


}
