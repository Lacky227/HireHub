package com.fullstackfamily.profileservice.dto;

import com.fullstackfamily.profileservice.model.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateProfileRequest {
    private String firstName;
    private String lastName;
    private String contactEmail;
    private String contactPhone;

    private String headLine;
    private String location;
    private String role;
    private String aboutMe;

    private List<WorkExperience> workExperiences = new ArrayList<>();
    private List<Education> educations = new ArrayList<>();
    private List<String> skills = new ArrayList<>();
    private List<Language> languages = new ArrayList<>();

    private BigDecimal expectedSalary;

    private EmploymentType employmentType;

    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;
}
