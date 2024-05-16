package com.ubb.mentormind.repository;

import com.ubb.mentormind.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
