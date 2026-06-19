package com.fileservice.metadata.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;


public record CreateFileRequest(
        @NotBlank String fileName,
        @NotBlank String mimeType,
        @NotNull Long fileSize,
        @NotNull UUID userId
) {}

