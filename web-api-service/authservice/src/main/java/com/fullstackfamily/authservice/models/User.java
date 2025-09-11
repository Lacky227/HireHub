package com.fullstackfamily.authservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Token> tokens =  new ArrayList<>();

    public void addToken(Token token) {
        tokens.add(token);
        token.setUser(this);
    }
}
