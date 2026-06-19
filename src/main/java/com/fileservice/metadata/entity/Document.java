package com.fileservice.metadata.entity;

import com.fileservice.metadata.util.DocumentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "documents")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity {

    @Id
    private Long id;

    private String title;

    @Column(name = "current_version")
    private Integer currentVersion;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @Column(name = "source_type")
    private String sourceType;

    private String language;

    @Column(columnDefinition = "jsonb")
    private String tags;

}

