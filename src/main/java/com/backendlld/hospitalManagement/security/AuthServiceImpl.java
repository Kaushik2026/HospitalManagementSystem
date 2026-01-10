package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.dtos.LoginRequestDto;
import com.backendlld.hospitalManagement.dtos.LoginResponseDto;
import com.backendlld.hospitalManagement.dtos.SignUpRequestDto;
import com.backendlld.hospitalManagement.dtos.SignupResponseDto;
import com.backendlld.hospitalManagement.model.Patient;
import com.backendlld.hospitalManagement.model.User;
import com.backendlld.hospitalManagement.repository.PatientRepository;
import com.backendlld.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }


    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("User already exists");

        user = userRepository.save(User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build());
        return new SignupResponseDto(user.getId(), user.getUsername());
    }


}