package com.fullstackfamily.profileservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Language {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;
    private String level;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
