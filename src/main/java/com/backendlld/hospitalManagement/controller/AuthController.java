package com.backendlld.hospitalManagement.controller;

import com.backendlld.hospitalManagement.dtos.LoginRequestDto;
import com.backendlld.hospitalManagement.dtos.LoginResponseDto;
import com.backendlld.hospitalManagement.dtos.SignUpRequestDto;
import com.backendlld.hospitalManagement.dtos.SignupResponseDto;
import com.backendlld.hospitalManagement.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }
}