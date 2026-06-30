package com.fileservice.metadata.entity;

import java.time.Instant;
import com.fileservice.metadata.util.ProcessignStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "document_processing")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProcessing {

    @Id
    private Long documentProcessingId;

    @Column(name = "document_version_id")
    private Long documentVersionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "processing_status")
    private ProcessignStatus processingStatus;

    @Column(name = "processing_stage")
    private String processingStage;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "chunk_count")
    private Integer chunkCount;

    @Column(name = "embedding_model")
    private String embeddingModel;

    @Column(name = "vector_store")
    private String vectorStore;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;
}
