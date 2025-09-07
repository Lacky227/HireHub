package com.fullstackfamily.authservice.service;

import com.fullstackfamily.authservice.dto.LoginRequest;
import com.fullstackfamily.authservice.dto.MessageResponse;
import com.fullstackfamily.authservice.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<MessageResponse> login(LoginRequest loginRequest);
    ResponseEntity<MessageResponse> register(RegisterRequest registerRequest);
}
