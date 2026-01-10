package com.backendlld.hospitalManagement.repository;

import com.backendlld.hospitalManagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}