package com.fileservice.metadata.service;

import com.fileservice.metadata.repository.DocumentProcessingRepository;
import com.fileservice.metadata.repository.DocumentVersionRepository;
import com.fileservice.metadata.storage.objectstore.MinioObjectStorageService;
import org.springframework.data.redis.core.RedisTemplate;
import com.fileservice.metadata.dto.request.PresignedUrlRequest;
import com.fileservice.metadata.dto.response.PresignedUrlResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final DocumentProcessingRepository documentProcessingRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final MinioObjectStorageService minioObjectStorageService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SnowflakeIdGenerator 

    public PresignedUrlResponse getPresignedUrl(PresignedUrlRequest request) {
        Long fileId = 
        
    }

}

