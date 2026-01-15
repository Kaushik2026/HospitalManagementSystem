package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.dtos.LoginRequestDto;
import com.backendlld.hospitalManagement.dtos.LoginResponseDto;
import com.backendlld.hospitalManagement.dtos.SignUpRequestDto;
import com.backendlld.hospitalManagement.dtos.SignupResponseDto;
import com.backendlld.hospitalManagement.model.User;
import com.backendlld.hospitalManagement.model.enums.RoleType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    SignupResponseDto signup(SignUpRequestDto signupRequestDto);

    ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId);

    void assignRoleToUser(Long userId, RoleType role);
    void logOut(User user);
}
