package com.fileservice.metadata.util;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class SnowflakeIdGenerator {

    private static final long CUSTOM_EPOCH =
            Instant.parse("2025-01-01T00:00:00Z").toEpochMilli();

    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_WORKER_ID =
            ~(-1L << WORKER_ID_BITS);

    private static final long MAX_DATACENTER_ID =
            ~(-1L << DATACENTER_ID_BITS);

    private static final long SEQUENCE_MASK =
            ~(-1L << SEQUENCE_BITS);

    private static final long WORKER_ID_SHIFT =
            SEQUENCE_BITS;

    private static final long DATACENTER_ID_SHIFT =
            SEQUENCE_BITS + WORKER_ID_BITS;

    private static final long TIMESTAMP_SHIFT =
            SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private final long workerId;
    private final long datacenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(
            @Value("${snowflake.worker-id:1}") long workerId,
            @Value("${snowflake.datacenter-id:1}") long datacenterId) {

        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(
                    "Worker ID must be between 0 and " + MAX_WORKER_ID);
        }

        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_ID) {
            throw new IllegalArgumentException(
                    "Datacenter ID must be between 0 and " + MAX_DATACENTER_ID);
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {

        long timestamp = currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException(
                    "Clock moved backwards. Refusing for "
                            + (lastTimestamp - timestamp) + " ms");
        }

        if (timestamp == lastTimestamp) {

            sequence = (sequence + 1) & SEQUENCE_MASK;

            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }

        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - CUSTOM_EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {

        long timestamp = currentTimeMillis();

        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis();
        }

        return timestamp;
    }

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}