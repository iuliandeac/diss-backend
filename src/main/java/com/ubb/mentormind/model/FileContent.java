package com.ubb.mentormind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FileContent {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    Material material;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    byte[] data;
}
