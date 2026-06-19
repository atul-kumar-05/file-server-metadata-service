package com.fileservice.metadata.repository;

import com.fileservice.metadata.entity.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileByIdRepository extends JpaRepository<FileDocument, UUID> {


    Optional<FileDocument> findByFileId(UUID fileId);

    List<FileDocument> findByUserId(UUID userId);
}

