package com.masterswork.storage.repository;

import com.masterswork.storage.model.StoredFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {

    Optional<StoredFile> findByPath(String path);

    Page<StoredFile> findAllByCreatedByOrModifiedBy(String createdBy, String modifiedBy, Pageable pageable);

    boolean existsByPath(String path);
}
