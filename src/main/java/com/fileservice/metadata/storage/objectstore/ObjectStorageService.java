package com.fileservice.metadata.storage.objectstore;

import java.io.InputStream;

public interface ObjectStorageService {
    void uploadFile(String bucketName, String objectName, InputStream inputStream, long size, String contentType) throws Exception;
    InputStream downloadFile(String bucketName, String objectName) throws Exception;
    void deleteFile(String bucketName, String objectName) throws Exception;
    String getPresignedUrl(String bucketName, String objectName, int expiryInSeconds) throws Exception;
    String getPresignedUploadUrl(String bucketName, String objectName, int expiryInSeconds) throws Exception;
}
