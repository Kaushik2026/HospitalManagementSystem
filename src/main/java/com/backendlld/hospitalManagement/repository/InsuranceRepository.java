package com.backendlld.hospitalManagement.repository;

import com.backendlld.hospitalManagement.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}