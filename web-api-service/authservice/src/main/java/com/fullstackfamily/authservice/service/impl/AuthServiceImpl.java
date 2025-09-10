package com.fullstackfamily.authservice.service.impl;

import com.fullstackfamily.authservice.dto.AuthResponse;
import com.fullstackfamily.authservice.dto.LoginRequest;
import com.fullstackfamily.authservice.dto.MessageResponse;
import com.fullstackfamily.authservice.dto.RegisterRequest;
import com.fullstackfamily.authservice.models.User;
import com.fullstackfamily.authservice.repository.AuthRepository;
import com.fullstackfamily.authservice.service.AuthService;
import com.fullstackfamily.authservice.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> user = authRepository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid email or password"));
        } else if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid email or password"));
        }
        return ResponseEntity.ok(new AuthResponse(
                jwtService.generateJwtToken(user.get().getEmail(), user.get().getRole().toString()),
                user.get().getRole().toString()));
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (authRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Email already exists"));
        }
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        authRepository.save(user);
        return ResponseEntity.ok(new AuthResponse(
                jwtService.generateJwtToken(user.getEmail(), user.getRole().toString()),
                user.getRole().toString()));
    }
}
