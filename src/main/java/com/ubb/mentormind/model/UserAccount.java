package com.ubb.mentormind.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserAccount {
    @Column(unique = true)
    String email;
    String firstName;
    String lastName;
    String role;
    String password;
    @Id
    @GeneratedValue
    private Long id;
}
