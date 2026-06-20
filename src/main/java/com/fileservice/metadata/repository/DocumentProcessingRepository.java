package com.fileservice.metadata.repository;

import com.fileservice.metadata.entity.DocumentProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentProcessingRepository extends JpaRepository<DocumentProcessing, Long> {
}
