package com.fileservice.metadata.service;

import com.fileservice.metadata.entity.Document;
import com.fileservice.metadata.entity.DocumentProcessing;
import com.fileservice.metadata.entity.DocumentVersion;
import com.fileservice.metadata.repository.DocumentProcessingRepository;
import com.fileservice.metadata.repository.DocumentVersionRepository;
import com.fileservice.metadata.storage.objectstore.MinioObjectStorageService;
import com.fileservice.metadata.storage.objectstore.MinioProperties;
import com.fileservice.metadata.dto.request.PresignedUrlRequest;
import com.fileservice.metadata.dto.response.PresignedUrlResponse;

import java.time.Instant;

import com.fileservice.metadata.util.DocumentStatus;
import com.fileservice.metadata.util.ProcessignStatus;
import com.fileservice.metadata.util.UploadStatus;
import org.springframework.stereotype.Service;
import com.fileservice.metadata.util.SnowflakeIdGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final DocumentProcessingRepository documentProcessingRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final MinioObjectStorageService minioObjectStorageService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final MinioProperties minioProperties;

    public PresignedUrlResponse getPresignedUrl(PresignedUrlRequest request) {
        long fileId = snowflakeIdGenerator.nextId();
        long versionId = snowflakeIdGenerator.nextId();
        long processingId = snowflakeIdGenerator.nextId();
        
        String blobPath = String.format("%s/%s", fileId, request.fileName());

        long expiryInSeconds = 3600L; // 1 hour
        String presignedUrl;
        try {
            presignedUrl = minioObjectStorageService.getPresignedUploadUrl(
                    minioProperties.getBucketName(),
                    blobPath,
                    (int) expiryInSeconds
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate presigned upload URL", e);
        }

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
                .uploadedAt(Instant.now())
                .build();
        documentVersionRepository.save(documentVersion);

        // 5. Save Document Processing metadata
        DocumentProcessing documentProcessing = DocumentProcessing.builder()
                .documentProcessingId(snowflakeIdGenerator.nextId())
                .documentVersionId(versionId)
                .processingStatus(ProcessignStatus.PENDING)
                .startedAt(Instant.now())
                .build();
        documentProcessingRepository.save(documentProcessing);

        // 6. Return response
        return new PresignedUrlResponse(fileId, blobPath, expiryInSeconds, presignedUrl);
    }

}

