package com.fileservice.metadata.storage;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobConfig {

    @Bean
    BlobServiceClient blobServiceClient(StorageProperties properties) {

        return new BlobServiceClientBuilder()
                .connectionString(properties.getConnectionString())
                .buildClient();
    }

    @Bean
    BlobContainerClient blobContainerClient(BlobServiceClient blobServiceClient, StorageProperties properties) {

        BlobContainerClient container = blobServiceClient.getBlobContainerClient(properties.getContainerName());

        if (!container.exists()) {
            container.create();
        }

        return container;
    }
}