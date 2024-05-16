package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Lecture {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    String icon;

    @ManyToOne
    @JoinColumn(name="author")
    UserAccount author;

    @OneToMany
    Set<Material> materials;
}