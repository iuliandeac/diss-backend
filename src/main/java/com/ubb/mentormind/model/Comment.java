package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @ManyToOne
    @JoinColumn(name = "author")
    UserAccount author;
    @ManyToOne
    @JoinColumn(name = "replyTo")
    Comment replyTo;
    String content;
    Long timestamp = System.currentTimeMillis();
    Long anchor;
    @Id
    @GeneratedValue
    private Long id;
}
