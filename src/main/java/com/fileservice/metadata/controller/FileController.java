package com.fileservice.metadata.controller;

import com.fileservice.metadata.dto.request.CreateFileRequest;
import com.fileservice.metadata.dto.response.FileResponse;
import com.fileservice.metadata.entity.FileDocument;
import com.fileservice.metadata.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FileResponse> createFile(@Valid @RequestBody CreateFileRequest request) {
        FileResponse response = fileService.createFile(request,request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileDocument> getById(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fileService.getFileById(fileId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FileDocument>> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(Collections.singletonList(fileService.getFileById(userId)));
    }
}

