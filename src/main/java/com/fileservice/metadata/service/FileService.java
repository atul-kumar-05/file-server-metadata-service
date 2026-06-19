package com.fileservice.metadata.service;

import com.fileservice.metadata.dto.request.CreateFileRequest;
import com.fileservice.metadata.dto.response.FileResponse;
import com.fileservice.metadata.entity.FileDocument;
import com.fileservice.metadata.exception.FileNotFoundException;
import com.fileservice.metadata.kafka.event.FileCreatedEvent;
import com.fileservice.metadata.kafka.producer.FileEventProducer;
import com.fileservice.metadata.repository.FileByIdRepository;
import com.fileservice.metadata.service.redis.CacheService;
import com.fileservice.metadata.util.DocumentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final CacheService cacheService;
    private final FileByIdRepository fileByIdRepository;
    private final FileEventProducer fileEventProducer;

    public FileResponse createFile(CreateFileRequest request,UUID userId) {
        FileDocument entity = FileDocument.builder()
                .fileId(UUID.randomUUID())
                .userId(userId)
                .fileName(request.fileName())
                .mimeType(request.mimeType())
                .fileSize(request.fileSize())
                .status(DocumentStatus.INITIATED)
                .createdAt(Instant.now())
                .build();

        FileDocument file = fileByIdRepository.save(entity);

        cacheService.set(getKey(file.getFileId()), file);

        FileCreatedEvent event = FileCreatedEvent.builder()
                .eventId(UUID.randomUUID())
                .fileId(entity.getFileId())
                .userId(userId)
                .createdAt(Instant.now())
                .build();

        fileEventProducer.publish("create-file",event);

        return FileResponse.builder()
                .fileId(entity.getFileId())
                .status(entity.getStatus().name())
                .build();
    }

    public FileDocument getFileById(UUID fileId) {
        FileDocument file = cacheService.get(getKey(fileId), FileDocument.class);
        if (file != null) {
            return file;
        }
        file = fileByIdRepository.findByFileId(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
        cacheService.set(getKey(file.getFileId()), file);
        return file;
    }

    public List<FileDocument> findByUserId(UUID userId) {
        return fileByIdRepository.findByUserId(userId);
    }

    private String getKey(UUID fileId) {
        return "file:"+fileId.toString();
    }
}

