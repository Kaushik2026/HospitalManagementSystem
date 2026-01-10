package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.dtos.LoginRequestDto;
import com.backendlld.hospitalManagement.dtos.LoginResponseDto;
import com.backendlld.hospitalManagement.dtos.SignUpRequestDto;
import com.backendlld.hospitalManagement.dtos.SignupResponseDto;
import com.backendlld.hospitalManagement.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    SignupResponseDto signup(SignUpRequestDto signupRequestDto);

}
