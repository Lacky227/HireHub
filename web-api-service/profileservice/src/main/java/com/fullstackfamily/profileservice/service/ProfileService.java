package com.fullstackfamily.profileservice.service;

import com.fullstackfamily.profileservice.dto.CreateProfileRequest;
import com.fullstackfamily.profileservice.dto.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    ResponseEntity<?> getProfiles();
    ResponseEntity<?> getProfileByEmail(String email);
    ResponseEntity<?> createProfile(CreateProfileRequest createProfileRequest);
    ResponseEntity<?> updateProfile(UpdateProfileRequest updateProfileRequest);
    ResponseEntity<?> deleteProfile(String email);
}
