package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Subject {
    String name;
    String icon;
    String joinCode;
    boolean isApprovalNeeded;
    @ManyToOne
    @JoinColumn(name = "teacher")
    UserAccount teacher;
    @ManyToMany
    Set<UserAccount> participants;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Lecture> lectures;
    @Id
    @GeneratedValue
    private Long id;
}