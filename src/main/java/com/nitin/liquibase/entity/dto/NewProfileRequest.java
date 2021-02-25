package com.nitin.liquibase.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewProfileRequest {
    private Long studentId;

    private String wand;
    private String house;
    private String description;
    private String imageUrl;
    private Character gender;
}
