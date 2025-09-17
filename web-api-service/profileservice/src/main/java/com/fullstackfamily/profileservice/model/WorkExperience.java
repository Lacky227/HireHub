package com.fullstackfamily.profileservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class WorkExperience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String position;

    private LocalDate startDate;
    private LocalDate endDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
