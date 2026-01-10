package com.backendlld.hospitalManagement.dtos;

import com.backendlld.hospitalManagement.model.enums.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
}