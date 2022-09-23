package com.masterswork.account.service;

import com.masterswork.account.api.dto.role.RoleCreateDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.api.dto.role.RoleUpdateDTO;

import java.util.List;

public interface RoleService {

    RoleResponseDTO createRole(RoleCreateDTO facultyResponseDTO);

    RoleResponseDTO updateRole(Long id, RoleUpdateDTO facultyResponseDTO);

    RoleResponseDTO patchRole(Long id, RoleUpdateDTO facultyResponseDTO);

    List<RoleResponseDTO> getAllRoles();

    List<RoleResponseDTO> getAllRolesByAccountId(Long accountId);

    List<RoleResponseDTO> getAllRolesByUsername(String username);

    RoleResponseDTO getRole(Long id);

    void deleteRole(Long id);
}
