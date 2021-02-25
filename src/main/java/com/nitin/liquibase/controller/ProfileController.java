package com.nitin.liquibase.controller;

import com.nitin.liquibase.entity.dto.NewProfileRequest;
import com.nitin.liquibase.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    private ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "/profile", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity createProfile(NewProfileRequest newProfileRequest) {
        profileService.addProfile(newProfileRequest);
        return ResponseEntity.ok().build();
    }

}
