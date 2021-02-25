package com.nitin.liquibase.service;

import com.nitin.liquibase.entity.Profile;
import com.nitin.liquibase.entity.dto.NewProfileRequest;
import com.nitin.liquibase.repository.ProfileRepository;
import com.nitin.liquibase.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final StudentRepository studentRepository;

    public ResponseEntity<Object> addProfile(NewProfileRequest newProfileRequest) {
        Profile profile = new Profile();

        profile.setWand(newProfileRequest.getWand());
        profile.setHouse(newProfileRequest.getHouse());
        profile.setDescription(newProfileRequest.getDescription());
        profile.setImageUrl("/img/sample.jpg");

        if (newProfileRequest.getStudentId() != null) {
            profile.setStudent(studentRepository.findById(newProfileRequest.getStudentId())
                    .orElse(null));
        }

        Profile savedProfile = profileRepository.save(profile);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProfile.getId())
                .toUri();

        //Returns the created resource URI in response header
        return (ResponseEntity<Object>) ResponseEntity.created(location).build();
    }
}