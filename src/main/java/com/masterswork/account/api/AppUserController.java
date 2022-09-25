package com.masterswork.account.api;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.CathedraService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Operation(summary = "Create new appUser")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> createAppUser(@Valid @RequestBody AppUserCreateDTO appUserCreateDTO) {
        return ResponseEntity.ok(appUserService.createAppUser(appUserCreateDTO));
    }

    @Operation(summary = "Assign cathedra to appUser")
    @PutMapping(path = "/{userId}/cathedras/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> assignCathedra(@PathVariable Long userId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(appUserService.assignCathedra(userId, cathedraId));
    }

    @Operation(summary = "Unassign cathedra from appUser")
    @DeleteMapping(path = "/{userId}/cathedras/{cathedraId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> unassignCathedra(@PathVariable Long userId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(appUserService.unassignCathedra(userId, cathedraId));
    }

    @Operation(summary = "Update appUser by userId")
    @PutMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody AppUserUpdateDTO body) {
        return ResponseEntity.ok(appUserService.updateUser(userId, body));
    }

    @Operation(summary = "Patch appUser by userId")
    @PatchMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> patchUser(@PathVariable Long userId, @RequestBody AppUserUpdateDTO body) {
        return ResponseEntity.ok(appUserService.patchUser(userId, body));
    }

    @Operation(summary = "Get appUser by userId")
    @GetMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(appUserService.getUserById(userId));
    }

    @Operation(summary = "Get appUser for current user")
    @GetMapping(path = "/current", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> getUserCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(appUserService.getUserByAccountUsername(authentication.getName()));
    }

    @Operation(summary = "Get all appUsers")
    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<AppUserResponseDTO>> getAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAllAppUsers());
    }

    @Operation(summary = "Get cathedras for appUser by userId")
    @GetMapping(path = "/{userId}/cathedras", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CathedraResponseDTO>> getAllCathedrasForAppUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cathedraService.getAllCathedrasByAppUserId(userId));
    }

    @Operation(summary = "Get all available types of users")
    @GetMapping(path = "/types", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Set<String>> getAllUserTypes() {
        return ResponseEntity.ok(appUserService.getAllUserTypes());
    }

    @Operation(summary = "Delete appUser by userId")
    @DeleteMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        appUserService.deleteAppUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
