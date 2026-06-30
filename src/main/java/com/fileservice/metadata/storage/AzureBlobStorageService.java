package com.fileservice.metadata.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService implements ObjectStorageService {

    private final BlobContainerClient containerClient;

    @Override
    public String generateUploadUrl(String blobName, Duration expiry) {

        BlobClient blobClient = containerClient.getBlobClient(blobName);

        OffsetDateTime expiryTime = OffsetDateTime.now().plus(expiry);

        BlobServiceSasSignatureValues values =
                new BlobServiceSasSignatureValues(expiryTime,
                        new BlobSasPermission().setCreatePermission(true).setWritePermission(true));

        return blobClient.getBlobUrl() + "?" + blobClient.generateSas(values);
    }

    @Override
    public boolean blobExists(String blobName) {

        return containerClient
                .getBlobClient(blobName)
                .exists();
    }
}