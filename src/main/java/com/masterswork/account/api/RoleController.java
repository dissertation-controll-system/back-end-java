package com.masterswork.account.api;

import com.masterswork.account.api.dto.role.RoleCreateDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.api.dto.role.RoleUpdateDTO;
import com.masterswork.account.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleCreateDTO body) {
        var newEntity = roleService.createRole(body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(newEntity);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long roleId, @Valid @RequestBody RoleUpdateDTO body) {
        var updatedEntity = roleService.updateRole(roleId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedEntity);
    }

    @PatchMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> patchRole(@PathVariable Long roleId, @RequestBody RoleUpdateDTO body) {
        var patchedEntity = roleService.patchRole(roleId, body);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patchedEntity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(patchedEntity);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.getRole(roleId));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

}
