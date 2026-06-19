package com.fileservice.metadata.kafka.producer;

import com.fileservice.metadata.kafka.event.FileCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileEventProducer {
    private final KafkaTemplate<String, FileCreatedEvent> kafkaTemplate;

    public void publish(String topic,FileCreatedEvent fileCreatedEvent) {
        kafkaTemplate.send(topic, String.valueOf(fileCreatedEvent.fileId()),fileCreatedEvent)
                .whenComplete((metadataEvent, throwable) -> {
                    if (throwable != null) {
                        log.error(throwable.getMessage(), throwable);
                        return;
                    }
                    log.info("Publishing file metadata event to topic {} with fileId {}", topic, fileCreatedEvent.fileId());
                });
    }
}

