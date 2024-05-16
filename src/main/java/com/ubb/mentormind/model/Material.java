package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Material {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    @ManyToOne
    @JoinColumn(name="author")
    UserAccount author;

    @ManyToMany
    Set<UserAccount> completedBy;

    String size;

    String description;

    Boolean isAccepted;

    Long timestamp = System.currentTimeMillis();

    enum type {
        text,
        pdf,
        video,
        jpeg
    }

    @OneToMany
    Set<Comment> comments;
}
