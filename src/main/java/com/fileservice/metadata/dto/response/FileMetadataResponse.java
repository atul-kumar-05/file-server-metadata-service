package com.fileservice.metadata.dto.response;

import java.time.Instant;

public record FileMetadataResponse(
        String fileId,
        String userId,
        String fileName,
        String contentType,
        long size,
        Instant createdAt
) {
}

