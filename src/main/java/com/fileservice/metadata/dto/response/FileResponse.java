package com.fileservice.metadata.dto.response;

import lombok.Builder;
import java.util.UUID;

@Builder
public record FileResponse(
        UUID fileId,
        String status
) {
}

