package com.backendlld.hospitalManagement.service;

import com.backendlld.hospitalManagement.dtos.DoctorResponseDto;
import com.backendlld.hospitalManagement.dtos.OnboardDoctorRequestDto;
import com.backendlld.hospitalManagement.model.Department;
import com.backendlld.hospitalManagement.model.Doctor;
import com.backendlld.hospitalManagement.model.User;
import com.backendlld.hospitalManagement.model.enums.RoleType;
import com.backendlld.hospitalManagement.repository.DepartmentRepository;
import com.backendlld.hospitalManagement.repository.DoctorRepository;
import com.backendlld.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onBoardDoctorRequestDto) {
        User user = userRepository.findById(onBoardDoctorRequestDto.getUserId()).orElseThrow();

        if(doctorRepository.existsById(onBoardDoctorRequestDto.getUserId())) {
            throw new IllegalArgumentException("Already a doctor");
        }

        Doctor doctor = Doctor.builder()
                .name(onBoardDoctorRequestDto.getName())
                .specialization(onBoardDoctorRequestDto.getSpecialization())
                .user(user)
                .build();

        user.getRoles().add(RoleType.DOCTOR);

        return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
    }

    @Transactional
    @Override
    public DoctorResponseDto makeHOD(Long doctorId, Long departmentId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Department department = departmentRepository.findById(departmentId).orElseThrow();

         department.setHeadOfDepartment(doctor);
         departmentRepository.save(department);

         doctor.getDepartments().add(department);
         doctor = doctorRepository.save(doctor);

        return modelMapper.map(doctor, DoctorResponseDto.class);

    }

    @Override
    public List<DoctorResponseDto> searchDoctorsBySpeciality(String specialty) {
        List<Doctor> doctorList =  doctorRepository.findBySpecializationContainingIgnoreCase(specialty);

        return modelMapper.map(doctorList, new TypeToken<List<DoctorResponseDto>>(){}.getType());

    }
}
