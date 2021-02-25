package com.nitin.liquibase.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "PROFILES")
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRO_STUD_ID")
    private Student student;

    @Column(name = "WAND")
    private String wand;

    @Column(name = "HOUSE")
    private String house;

    @Column(name = "PRO_DESCRIPTION")
    private String description;

    @Column(name = "PRO_IMAGE_URL")
    private String imageUrl;
}
