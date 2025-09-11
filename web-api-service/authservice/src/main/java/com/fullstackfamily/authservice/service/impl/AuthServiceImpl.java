package com.fullstackfamily.authservice.service.impl;

import com.fullstackfamily.authservice.dto.*;
import com.fullstackfamily.authservice.models.Token;
import com.fullstackfamily.authservice.models.User;
import com.fullstackfamily.authservice.repository.AuthRepository;
import com.fullstackfamily.authservice.repository.TokenRepository;
import com.fullstackfamily.authservice.service.AuthService;
import com.fullstackfamily.authservice.service.JwtService;
import com.fullstackfamily.authservice.utility.UUIDUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final TokenRepository tokenRepository;
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

        String newAccessToken = jwtService.generateJwtToken(
                user.get().getEmail(),
                user.get().getRole().toString());
        String refreshToken = UUIDUtility.getUUID();
        createNewToken(user.get(), refreshToken);
        authRepository.save(user.get());

        return ResponseEntity.ok(new AuthResponse(
                jwtService.generateJwtToken(
                        user.get().getEmail(),
                        user.get().getRole().toString()),
                refreshToken,
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

        String newAccessToken = jwtService.generateJwtToken(
                user.getEmail(),
                user.getRole().toString());
        String refreshToken = UUIDUtility.getUUID();
        createNewToken(user, refreshToken);
        authRepository.save(user);

        return ResponseEntity.ok(new AuthResponse(
                newAccessToken,
                refreshToken,
                user.getRole().toString()));
    }

    @Override
    public ResponseEntity<?> refresh(String refreshToken) {
        Optional<Token> oldToken = tokenRepository.findByToken(refreshToken);
        if (oldToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        } else if (oldToken.get().getRevoked() || oldToken.get().getExpiredAt().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(oldToken.get());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = oldToken.get().getUser();
        tokenRepository.delete(oldToken.get());

        String newAccessToken = jwtService.generateJwtToken(
                user.getEmail(),
                user.getRole().toString());
        String newRefreshToken = UUIDUtility.getUUID();
        createNewToken(user, newRefreshToken);
        authRepository.save(user);

        return ResponseEntity.ok(new AuthResponse(
                newAccessToken,
                newRefreshToken,
                user.getRole().toString()));
    }

    private void createNewToken(User user, String token) {
        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setCreatedAt(LocalDateTime.now());
        newToken.setExpiredAt(LocalDateTime.now().plusDays(30));
        newToken.setRevoked(false);
        user.addToken(newToken);
    }
}
