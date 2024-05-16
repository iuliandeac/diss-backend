package com.ubb.mentormind.repository;

import com.ubb.mentormind.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
