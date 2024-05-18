package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Lecture {
    String name;
    String icon;
    @ManyToOne
    @JoinColumn(name = "author")
    UserAccount author;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Material> materials;
    @Id
    @GeneratedValue
    private Long id;
}