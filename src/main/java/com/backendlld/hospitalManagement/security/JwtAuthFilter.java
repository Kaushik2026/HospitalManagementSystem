package com.backendlld.hospitalManagement.security;

import com.backendlld.hospitalManagement.model.User;
import com.backendlld.hospitalManagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

//    @Autowired  // Field injection ONLY for resolver
//    @Qualifier("handlerExceptionResolver")
//    private final HandlerExceptionResolver resolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            log.info("incomming request: {}", request.getRequestURI());
            final String requestTokenHeader = request.getHeader("Authorization");
//            requestTokenHeader should be in format "Bearer token". we need to extract token only
            if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token  = requestTokenHeader.split("Bearer ")[1].trim();
//            "Bearer","token" this array we will get after spliting. we need token only hence [1].
            String username = authUtil.getUsernameFromToken(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElseThrow();
                //*
                // UsernamePasswordAuthenticationToken = "This user is authenticated via username"
                //↓ Used by:
                //  - Form login (username/password)
                //  - JWT tokens (username from token) ← YOUR CASE
                //  - Basic Auth (username:password header)
                //  - Custom username providers
                //  *

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                2-arg constructor: new (user, password) → isAuthenticated() = FALSE
//                3-arg constructor: new (user, null, authorities) → isAuthenticated() = TRUE

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
//            resolver.resolveException(request, response,null, ex);
            throw new ServletException(ex);
        }


    }
}
