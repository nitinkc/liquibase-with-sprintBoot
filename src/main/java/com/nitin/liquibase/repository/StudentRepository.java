package com.nitin.liquibase.repository;

import com.nitin.liquibase.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
