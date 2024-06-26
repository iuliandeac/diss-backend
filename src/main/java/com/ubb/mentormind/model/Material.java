package com.ubb.mentormind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Material {
    String name;
    @ManyToOne
    @JoinColumn(name = "author")
    UserAccount author;
    @ManyToMany
    Set<UserAccount> completedBy;
    String size;
    String description;
    Boolean isAccepted;
    Long timestamp = System.currentTimeMillis();
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Comment> comments;
    @Id
    @GeneratedValue
    private Long id;
    String type;
    @MapsId
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "material")
    FileContent fileContent;
}
