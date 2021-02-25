package com.nitin.liquibase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileDto {

    private String wand;
    private String house;
    private String description;
    private String image;
    private Student student;
    private Character gender;

    public static ProfileDto from(Profile profile) {
        Student student = profile.getStudent() != null ? profile.getStudent() : null;

        ProfileDto profileDto = new ProfileDto(profile.getWand(), profile.getHouse(),
                profile.getDescription(),
                profile.getImageUrl(),
                profile.getStudent(),
                profile.getGender());

        return profileDto;
    }
}
