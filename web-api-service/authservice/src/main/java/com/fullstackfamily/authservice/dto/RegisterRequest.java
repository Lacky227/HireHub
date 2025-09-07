package com.fullstackfamily.authservice.dto;

import com.fullstackfamily.authservice.models.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
