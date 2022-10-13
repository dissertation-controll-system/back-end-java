package com.masterswork.storage.service;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

	void init();

	StoredFileDTO store(MultipartFile file);

	Page<StoredFileDTO> loadAll(Pageable pageable);

	Page<StoredFileDTO> loadAllByUsername(String username, Pageable pageable);

	Path load(String filename);

	Resource loadAsResource(String filename);

}
