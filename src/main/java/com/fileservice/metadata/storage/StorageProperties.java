package com.fileservice.metadata.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "azure.storage")
public class StorageProperties {

    private String connectionString;
    private String containerName;

}