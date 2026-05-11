package com.fileservice.metadata.entity;

import com.fileservice.metadata.util.FileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Table("file_by_id")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileById {
    @PrimaryKey
    @Column("file_id")
    private UUID fileId;
    @Column("user_id")
    private UUID userId;
    @Column("file_name")
    private String fileName;
    @Column("mime_type")
    private String mimeType;
    @Column("file_size")
    private Long fileSize;
    @Column("storage_path")
    private String storagePath;
    @Column("status")
    private FileStatus Status;
    @Column("created_at")
    private Instant createdAt;
}
