package com.masterswork.account.api;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.CathedraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final CathedraService cathedraService;

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> createAppUser(@Valid @RequestBody AppUserCreateDTO appUserCreateDTO) {
        return ResponseEntity.ok(appUserService.createAppUser(appUserCreateDTO));
    }

    @PutMapping("/{userId}/cathedras/{cathedraId}")
    public ResponseEntity<AppUserResponseDTO> assignCathedra(@PathVariable Long userId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(appUserService.assignCathedra(userId, cathedraId));
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponseDTO>> getAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAllAppUsers());
    }

    @GetMapping("/{id}/cathedras")
    public ResponseEntity<List<CathedraResponseDTO>> getAllCathedrasForAppUser(@PathVariable Long id) {
        return ResponseEntity.ok(cathedraService.getAllCathedrasByAppUserId(id));
    }

    @GetMapping("/types")
    public ResponseEntity<Set<String>> getAllUserTypes() {
        return ResponseEntity.ok(appUserService.getAllUserTypes());
    }
}
