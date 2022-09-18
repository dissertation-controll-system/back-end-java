package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.role.RoleCreateDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.api.dto.role.RoleUpdateDTO;
import com.masterswork.account.model.Role;
import com.masterswork.account.repository.RoleRepository;
import com.masterswork.account.service.RoleService;
import com.masterswork.account.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDTO createRole(RoleCreateDTO roleResponseDTO) {
        Role role = roleRepository.save(roleMapper.createFrom(roleResponseDTO));
        return roleMapper.toDto(role);
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleUpdateDTO roleResponseDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No role with id: " + id));
        roleMapper.updateFrom(role, roleResponseDTO);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public RoleResponseDTO patchRole(Long id, RoleUpdateDTO roleResponseDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No role with id: " + id));
        roleMapper.patchFrom(role, roleResponseDTO);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        List<Role> all = roleRepository.findAll();
        return roleMapper.toDto(all);
    }

    @Override
    public RoleResponseDTO getRole(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No role with id: " + id));
    }

    @Override
    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No role with id: " + id);
        }
    }
}
