package com.fileservice.metadata.entity;

import com.fileservice.metadata.util.FileStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Document(collation = "files")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDocument extends BaseEntity{
    @Id
    private String id;
    @Field("file_id")
    @Indexed(unique = true)
    private UUID fileId;
    @Field("user_id")
    private UUID userId;
    @Field("file_name")
    private String fileName;
    @Field("mime_type")
    private String mimeType;
    @Field("file_size")
    private Long fileSize;
    @Field("storage_path")
    private String storagePath;
    @Field("status")
    private FileStatus status;
    Map<String, Object> metadata;
}
