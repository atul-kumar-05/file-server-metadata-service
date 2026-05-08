package com.fileservice.metadata.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    private Boolean deleted;
    private Instant createdAt;
    private Instant updatedAt;
}
