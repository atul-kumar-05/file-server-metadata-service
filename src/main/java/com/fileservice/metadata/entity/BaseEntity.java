package com.fileservice.metadata.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Field("deleted")
    private Boolean deleted = false;
    @Field("deleted_at")
    private Instant deletedAt;
    @Field("created_at")
    @CreatedDate
    private Instant createdAt;
    @Field("updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}
