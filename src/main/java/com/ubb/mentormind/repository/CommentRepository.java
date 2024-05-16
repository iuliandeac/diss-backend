package com.ubb.mentormind.repository;

import com.ubb.mentormind.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
