package com.backendlld.hospitalManagement.repository;

import com.backendlld.hospitalManagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}