package com.fullstackfamily.authservice.utility;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UUIDUtility {
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
