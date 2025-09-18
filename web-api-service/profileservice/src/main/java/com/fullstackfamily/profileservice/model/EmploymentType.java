package com.fullstackfamily.profileservice.model;

import java.util.Arrays;
import java.util.Optional;

public enum EmploymentType {
    FULL_TIME("full-time"),
    PART_TIME("part-time"),
    INTERNSHIP("internship"),
    CONTRACT("contract")

    ;
    private String value;
    EmploymentType(String value) {
        this.value = value;
    }
    public static Optional<EmploymentType> fromValue(String value) {
        return Arrays.stream(EmploymentType.values())
                .filter(e -> e.value.equals(value))
                .findFirst();
    }
}
