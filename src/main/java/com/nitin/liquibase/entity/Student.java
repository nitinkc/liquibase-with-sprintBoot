package com.nitin.liquibase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "STUDENT")
@Data
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "STUDENT_ID")
    private Long id;

    @Column(name = "STUDENT_FNAME")
    private String fName;

    @Column(name = "STUDENT_LNAME")
    private String lName;
}
