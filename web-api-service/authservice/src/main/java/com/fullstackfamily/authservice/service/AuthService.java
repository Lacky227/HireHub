package com.fullstackfamily.authservice.service;

import com.fullstackfamily.authservice.dto.LoginRequest;
import com.fullstackfamily.authservice.dto.MessageResponse;
import com.fullstackfamily.authservice.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(LoginRequest loginRequest);
    ResponseEntity<?> register(RegisterRequest registerRequest);
    ResponseEntity<?> refresh(String refreshToken);
}
