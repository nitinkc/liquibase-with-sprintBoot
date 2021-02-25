package com.nitin.liquibase.controller;

import com.nitin.liquibase.entity.dto.NewProfileRequest;
import com.nitin.liquibase.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    private ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "/profile_form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity createProfileWithForm(NewProfileRequest newProfileRequest) {
        profileService.addProfile(newProfileRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createProfileWithJson(@RequestBody NewProfileRequest newProfileRequest) {
        return profileService.addProfile(newProfileRequest);
    }

}
