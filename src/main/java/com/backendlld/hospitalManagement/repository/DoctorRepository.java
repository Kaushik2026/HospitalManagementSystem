package com.backendlld.hospitalManagement.repository;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecializationContainingIgnoreCaseAndNameContainingIgnoreCase(String specialty, String s);

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialty);
}