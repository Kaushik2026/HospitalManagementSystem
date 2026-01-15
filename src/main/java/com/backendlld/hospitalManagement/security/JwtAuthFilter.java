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

//             isTokenValid checks signature and expiration
            if (!authUtil.isTokenValid(token)) {
                log.warn("Invalid/expired token");
                String email = authUtil.getUsernameFromToken(token); // Safe even for expired
                if (email != null) {
                    User user = userRepository.findByUsername(email).orElse(null);
                    if (user != null && user.isLoggedIn()) {
                        user.setLoggedIn(false);  // Cleanup expired session
                        userRepository.save(user);
                        log.info("Auto-logged out expired session for: {}", email);
                    }
                }
                response.setStatus(401);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                return;
            }

            String username = authUtil.getUsernameFromToken(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElseThrow();
                if (!user.isLoggedIn()) {
                    log.warn("User {} session expired", username);

                    response.setStatus(401);
                    response.getWriter().write("{\"error\": \"Session expired. Please login again\"}");
                    return;
                }
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
