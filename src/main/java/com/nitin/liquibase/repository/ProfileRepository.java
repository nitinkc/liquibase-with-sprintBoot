package com.nitin.liquibase.repository;


import com.nitin.liquibase.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
