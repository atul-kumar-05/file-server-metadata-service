package com.fileservice.metadata.service;

import com.fileservice.metadata.entity.Document;
import com.fileservice.metadata.entity.DocumentProcessing;
import com.fileservice.metadata.entity.DocumentVersion;
import com.fileservice.metadata.repository.DocumentProcessingRepository;
import com.fileservice.metadata.repository.DocumentRepository;
import com.fileservice.metadata.repository.DocumentVersionRepository;
import com.fileservice.metadata.dto.request.PresignedUrlRequest;
import com.fileservice.metadata.dto.response.PresignedUrlResponse;
import java.time.Duration;
import java.time.Instant;
import com.fileservice.metadata.storage.AzureBlobStorageService;
import com.fileservice.metadata.storage.StorageProperties;
import com.fileservice.metadata.util.DocumentStatus;
import com.fileservice.metadata.util.ProcessignStatus;
import com.fileservice.metadata.util.UploadStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fileservice.metadata.util.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final DocumentRepository documentRepository;
    private final DocumentProcessingRepository documentProcessingRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final AzureBlobStorageService azureBlobStorageService;
    private final StorageProperties  storageProperties;


    @Transactional
    public PresignedUrlResponse getPresignedUrl(PresignedUrlRequest request) {
        long fileId = snowflakeIdGenerator.nextId();
        long versionId = snowflakeIdGenerator.nextId();
        long processingId = snowflakeIdGenerator.nextId();
        
        String blobPath = String.format("%s/%s", fileId, request.fileName());

        long expiryInSeconds = 300L; // 5 second
        String presignedUrl = "hi";
        try {
            presignedUrl = azureBlobStorageService.generateUploadUrl(storageProperties.getContainerName(), Duration.ofSeconds(expiryInSeconds));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate presigned upload URL", e);
        }
        log.info("Presigned URL: {}", presignedUrl);
        // 3. Save Document metadata
        Document document = Document.builder()
                .id(fileId)
                .title(request.title())
                .currentVersion(1)
                .status(DocumentStatus.INITIATED)
                .sourceType(request.sourceType())
                .language(request.language())
                .build();
        documentRepository.save(document);

        // 4. Save Document Version metadata
        DocumentVersion documentVersion = DocumentVersion.builder()
                .documentVersionId(versionId)
                .documentId(fileId)
                .versionNumber(1)
                .fileName(request.fileName())
                .originalFileName(request.fileName())
                .blobPath(blobPath)
                .mimeType(request.contentType())
                .uploadStatus(UploadStatus.PENDING)
                .build();
        documentVersionRepository.save(documentVersion);

        // 5. Save Document Processing metadata
        DocumentProcessing documentProcessing = DocumentProcessing.builder()
                .documentProcessingId(processingId)
                .documentVersionId(versionId)
                .processingStatus(ProcessignStatus.PENDING)
                .startedAt(Instant.now())
                .build();
        documentProcessingRepository.save(documentProcessing);

        // 6. Return response
        return new PresignedUrlResponse(fileId, blobPath, expiryInSeconds, presignedUrl);
    }

}

