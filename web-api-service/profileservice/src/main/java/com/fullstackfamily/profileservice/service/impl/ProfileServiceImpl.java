package com.fullstackfamily.profileservice.service.impl;

import com.fullstackfamily.profileservice.dto.CreateProfileRequest;
import com.fullstackfamily.profileservice.dto.UpdateProfileRequest;
import com.fullstackfamily.profileservice.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    @Override
    public ResponseEntity<?> getProfiles() {
        return null;
    }

    @Override
    public ResponseEntity<?> getProfileByEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<?> createProfile(CreateProfileRequest createProfileRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateProfile(UpdateProfileRequest updateProfileRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProfile(String email) {
        return null;
    }
}
