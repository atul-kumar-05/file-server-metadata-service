package com.fileservice.metadata.kafka.event;


import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T>(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        String traceId,
        T payload
) {
}