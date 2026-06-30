package com.fileservice.metadata.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PresignedUrlRequest(
        @NotBlank(message = "title should not be blank")
        String title,

        @NotBlank(message = "file name must not ")
        String fileName,

        @NotBlank(message = "not blank")
        String contentType,

        String language,

        String sourceType
) {}

