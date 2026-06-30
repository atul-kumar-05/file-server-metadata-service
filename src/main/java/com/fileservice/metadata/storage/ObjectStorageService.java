package com.fileservice.metadata.storage;

import java.time.Duration;

public interface ObjectStorageService {

    String generateUploadUrl(String blobName, Duration expiry);

    boolean blobExists(String blobName);
}