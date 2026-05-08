package com.fileservice.metadata.util;

public enum FileStatus {
    INITIATED,
    UPLOAD_URL_GENERATED,
    UPLOADING,
    UPLOADED,
    SCANNING,
    PROCESSING,
    READY,
    FAILED,
    QUARANTINED,
    DELETED
}
