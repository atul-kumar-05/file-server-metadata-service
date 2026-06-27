package com.fileservice.metadata.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import com.fileservice.metadata.dto.request.PresignedUrlRequest;
import com.fileservice.metadata.dto.response.PresignedUrlResponse;
import com.fileservice.metadata.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
   
    @PostMapping("/presigned")
    public ResponseEntity<PresignedUrlResponse> uploadFileUrl(@Valid @RequestBody PresignedUrlRequest request) {
        PresignedUrlResponse response = fileService.getPresignedUrl(request);
        return ResponseEntity.ok(response);
    }


}

