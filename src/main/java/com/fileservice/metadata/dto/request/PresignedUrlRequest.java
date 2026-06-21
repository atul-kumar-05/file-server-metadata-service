package com.fileservice.metadata.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PresignedUrlRequest(
        @NotBlank
        String title,

        @NotBlank
        String fileName,

        @NotBlank
        String contentType,

        String language,

        String sourceType
) {}

