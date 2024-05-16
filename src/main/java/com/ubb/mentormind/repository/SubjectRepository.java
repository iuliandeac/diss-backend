package com.ubb.mentormind.repository;

import com.ubb.mentormind.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByJoinCode(String joinCode);
}
