package com.fullstackfamily.profileservice.model;

import java.util.Arrays;
import java.util.Optional;

public enum StatusType {
    ACTIVE("active"),
    ARCHIVED("archived");

    private String value;
    StatusType(String value) {
        this.value = value;
    }
    public static Optional<StatusType> fromValue(String value) {
        return Arrays.stream(StatusType.values())
                .filter(s -> s.value.equals(value))
                .findFirst();
    }
}
