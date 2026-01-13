package com.backendlld.hospitalManagement.security;


import com.backendlld.hospitalManagement.model.enums.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.logging.Handler;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**","/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name())
                        .requestMatchers("/doctors/**").hasAnyRole(RoleType.DOCTOR.name(), RoleType.ADMIN.name())
                        .requestMatchers("/patients/**").hasAnyRole(RoleType.PATIENT.name(), RoleType.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2 -> oAuth2
                        .failureHandler((request, response, exception) -> {
                            log.error("OAuth2 login failed: {}", exception.getMessage());
                            handlerExceptionResolver.resolveException(request, response, null, exception);
                        })
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandlingConfig ->
                        exceptionHandlingConfig.accessDeniedHandler((request, response, exception) -> {
                            log.error("Access denied: {}", exception.getMessage());
                            response.sendError(403, "Access Denied");
                            handlerExceptionResolver.resolveException(request, response, null, exception);
                        }));


        return http.build();


    }





//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.withUsername("patient")
//                .password(passwordEncoder.encode("pass"))
//                .roles("PATIENT")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }
}
