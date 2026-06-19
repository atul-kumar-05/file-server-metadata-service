package com.fileservice.metadata.kafka.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record FileCreatedEvent(
        UUID eventId,
        UUID fileId,
        UUID userId,
        Instant createdAt
) {
}
