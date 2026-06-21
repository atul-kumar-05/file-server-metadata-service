package com.fileservice.metadata.dto.response;

public record PresignedUrlResponse(
    Long fileId,
    String blobPath,
    Long expiryInSeconds,
    String presignedUrl
) {}
