package com.masterswork.storage.service.impl;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import com.masterswork.storage.config.properties.StorageProperties;
import com.masterswork.storage.model.StoredFile;
import com.masterswork.storage.repository.StoredFileRepository;
import com.masterswork.storage.service.StorageService;
import com.masterswork.storage.service.exception.StorageException;
import com.masterswork.storage.service.exception.StorageFileNotFoundException;
import com.masterswork.storage.service.mapper.StoredFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileSystemStorageService implements StorageService {

	private final StoredFileRepository storedFileRepository;
	private final StoredFileMapper storedFileMapper;
	private final StorageProperties storageProperties;

	private Path rootLocation;

	@PostConstruct
	public void construct() {
		this.rootLocation = Paths.get(storageProperties.getLocation());
	}

	@Override
	public StoredFileDTO store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			String username = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
					.map(Principal::getName)
					.orElse("system");
			String originalFilename = file.getOriginalFilename();

			Path path = Paths.get(username, originalFilename);
			Path destinationFile = this.rootLocation.resolve(path)
					.normalize()
					.toAbsolutePath();

			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}

			StoredFile storedFile = storedFileRepository.findByPath(originalFilename)
					.map(StoredFile::incrementVersion)
					.orElseGet(() -> StoredFile.builder()
							.path(originalFilename)
							.sizeBytes(file.getSize())
							.build());

			return storedFileMapper.toDto(storedFileRepository.save(storedFile));
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Page<StoredFileDTO> loadAll(Pageable pageable) {
		return storedFileRepository.findAll(pageable)
				.map(storedFileMapper::toDto);
	}

	@Override
	public Page<StoredFileDTO> loadAllByUsername(String username, Pageable pageable) {
		return storedFileRepository.findAllByCreatedByOrModifiedBy(username, username, pageable)
				.map(storedFileMapper::toDto);
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		if (!storedFileRepository.existsByPath(filename)) {
			throw new StorageFileNotFoundException("Could not read file: " + filename);
		}

		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
