package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    String icon;

    String joinCode;

    boolean isApprovalNeeded;

    @ManyToOne
    @JoinColumn(name="teacher")
    UserAccount teacher;

    @ManyToMany
    Set<UserAccount> participants;

    @OneToMany
    Set<Lecture> lectures;
}