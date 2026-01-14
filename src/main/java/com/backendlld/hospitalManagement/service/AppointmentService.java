package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.AppointmentResponseDto;
import com.backendlld.hospitalManagement.dtos.CreateAppointmentRequestDto;
import com.backendlld.hospitalManagement.model.Appointment;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto dto);
    AppointmentResponseDto reAssignAppointmentToAnotherDoctor(Long appointmentId, Long newDoctorId);

    List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long docId);
}
