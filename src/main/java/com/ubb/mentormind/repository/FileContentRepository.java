package com.ubb.mentormind.repository;

import com.ubb.mentormind.model.FileContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileContentRepository extends JpaRepository<FileContent, Long> {
}
