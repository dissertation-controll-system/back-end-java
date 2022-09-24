package com.masterswork.account.api;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.CathedraService;
import lombok.RequiredArgsConstructor;
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

    @DeleteMapping("/{userId}/cathedras/{cathedraId}")
    public ResponseEntity<AppUserResponseDTO> unassignCathedra(@PathVariable Long userId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(appUserService.unassignCathedra(userId, cathedraId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody AppUserUpdateDTO body) {
        return ResponseEntity.ok(appUserService.updateUser(userId, body));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<AppUserResponseDTO> patchUser(@PathVariable Long userId, @RequestBody AppUserUpdateDTO body) {
        return ResponseEntity.ok(appUserService.patchUser(userId, body));
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponseDTO>> getAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAllAppUsers());
    }

    @GetMapping("/{userId}/cathedras")
    public ResponseEntity<List<CathedraResponseDTO>> getAllCathedrasForAppUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cathedraService.getAllCathedrasByAppUserId(userId));
    }

    @GetMapping("/types")
    public ResponseEntity<Set<String>> getAllUserTypes() {
        return ResponseEntity.ok(appUserService.getAllUserTypes());
    }
}
