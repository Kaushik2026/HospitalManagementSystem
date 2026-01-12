package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.dtos.LoginResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Lazy
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    //    here we will collect information from token provided by user authserver(google,github)
//    and create a new user in our db if not exists.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
//        registration id tells which auth server is used like google,github
        String registrationId = token.getAuthorizedClientRegistrationId();

       ResponseEntity<LoginResponseDto> loginResponse = authService
               .handleOAuth2LoginRequest(oAuth2User,registrationId);

//        LoginResponseDto body = loginResponse.getBody();
//        String json = String.format("""
//            {
//                "jwt": "%s",
//                "userId": %d
//            }
//            """, body.getJwt(), body.getUserId());

       response.setStatus(loginResponse.getStatusCode().value());
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       response.getWriter().write(objectMapper.writeValueAsString(loginResponse.getBody()));
    }
}
