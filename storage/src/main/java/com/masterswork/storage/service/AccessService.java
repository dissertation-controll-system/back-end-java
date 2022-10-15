package com.masterswork.storage.service;

import com.masterswork.storage.api.dto.access.FileAccessPermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccessService {

    void grantAccess(Long id, FileAccessPermissionDTO fileAccessPermissionDTO);

    void revokeAccess(Long id, FileAccessPermissionDTO fileAccessPermissionDTO);

    Page<FileAccessPermissionDTO> getUserFilePermissionsById(Long fileId, String username, Pageable pageable);
}
