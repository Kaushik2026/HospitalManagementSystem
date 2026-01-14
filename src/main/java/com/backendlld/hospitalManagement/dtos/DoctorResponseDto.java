package com.backendlld.hospitalManagement.dtos;

import com.backendlld.hospitalManagement.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String specialization;
    private String email;
    private List<Department> HeadOfDepartments;
}