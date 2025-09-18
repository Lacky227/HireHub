package com.fullstackfamily.profileservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProfileFilterRequest {
    private String location;
    private String role;
    private String employmentType;
    private BigDecimal expectedSalaryFrom;
    private BigDecimal expectedSalaryTo;
    private List<String> skills;
    private List<String> languages;
    private String status;
}
