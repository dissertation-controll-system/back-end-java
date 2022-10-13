package com.masterswork.storage.api;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import com.masterswork.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLConnection;


@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<Page<StoredFileDTO>> listUploadedFiles(
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(storageService.loadAll(pageable));
    }

    @GetMapping("/current")
    public ResponseEntity<Page<StoredFileDTO>> listCurrentUsers(
            Authentication authentication,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(storageService.loadAllByUsername(authentication.getName(), pageable));
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/preview/{filename}")
    public ResponseEntity<Resource> previewFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        String mimeType = URLConnection.guessContentTypeFromName(file.getFilename());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore().mustRevalidate())
                .contentType(MediaType.valueOf(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<StoredFileDTO> handleFileUpload(@RequestParam("file") MultipartFile file) {
        var storedFileDTO = storageService.store(file);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{filename}")
                .buildAndExpand(storedFileDTO.getPath())
                .toUri();
        return ResponseEntity.created(location).body(storedFileDTO);
    }
}
