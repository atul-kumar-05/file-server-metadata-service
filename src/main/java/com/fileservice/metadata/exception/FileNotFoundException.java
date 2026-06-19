package com.fileservice.metadata.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String fileId) {
        super("File metadata not found for id: " + fileId);
    }
}

