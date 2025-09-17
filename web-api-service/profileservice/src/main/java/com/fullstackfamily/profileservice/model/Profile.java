package com.fullstackfamily.profileservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "profiles")
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String contactEmail;
    private String contactPhone;

    private String headLine;
    private String location;
    private String role;
    private String aboutMe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Education> educations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "profile_skills",  joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "skills")
    private List<String> skills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Language> languages = new ArrayList<>();

    private BigDecimal expectedSalary;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    private LocalDateTime lastUpdated;

    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;
    private String cvFileUrl;
    private String cvFileName;
}
