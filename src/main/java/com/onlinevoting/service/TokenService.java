package com.onlinevoting.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    private final JwtService jwtService;
    public TokenService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String extractEmailId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String emailId = null;
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7); // Remove "Bearer " prefix
            try {
                emailId = jwtService.extractUsername(jwt); // Extract email from token
            } catch (Exception e) {
                throw new RuntimeException("Invalid JWT token", e);
            }
        } else {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
        return emailId;
    }

}
