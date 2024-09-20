package com.refi.model.documents;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@MappedSuperclass
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(nullable = false)
    private String url;

    @Column(name = "upload_date", nullable = false)
    private Instant uploadDate;
}
