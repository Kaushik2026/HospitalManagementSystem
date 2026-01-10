package com.backendlld.hospitalManagement.dtos;

import com.backendlld.hospitalManagement.model.enums.BloodGroupType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.backendlld.hospitalManagement.model.Patient}
 */
@Value
public class PatientDto implements Serializable {
    String name;
    int age;
    String email;
    String gender;
    LocalDate birthDate;
    LocalDateTime createdAt;
    BloodGroupType bloodGroup;
}