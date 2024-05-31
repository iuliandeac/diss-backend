package com.ubb.mentormind.repository;

import com.ubb.mentormind.model.FileContent;
import com.ubb.mentormind.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByFileContent(FileContent fileContent);
}
