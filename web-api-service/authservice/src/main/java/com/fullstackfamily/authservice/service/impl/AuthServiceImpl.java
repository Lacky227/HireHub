package com.fullstackfamily.authservice.service.impl;

import com.fullstackfamily.authservice.dto.LoginRequest;
import com.fullstackfamily.authservice.dto.MessageResponse;
import com.fullstackfamily.authservice.dto.RegisterRequest;
import com.fullstackfamily.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public ResponseEntity<MessageResponse> login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> register(RegisterRequest registerRequest) {
        return null;
    }
}
