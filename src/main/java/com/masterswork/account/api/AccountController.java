package com.masterswork.account.api;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.api.dto.account.AccountUpdateDTO;
import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.service.AccountService;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.RoleService;
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

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AppUserService appUserService;
    private final RoleService roleService;

    @PostMapping("/{accountId}/app-users")
    private ResponseEntity<AppUserResponseDTO> createAppUserForAccount(
            @PathVariable Long accountId, @Valid @RequestBody AppUserCreateDTO body) {
        return ResponseEntity.ok(appUserService.createAppUserForAccount(accountId, body));
    }

    @PutMapping("/{accountId}")
    private ResponseEntity<AccountResponseDTO> updateAccount(
            @PathVariable Long accountId, @Valid @RequestBody AccountUpdateDTO body) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, body));
    }

    @PatchMapping("/{accountId}")
    private ResponseEntity<AccountResponseDTO> patchAccount(
            @PathVariable Long accountId, @RequestBody AccountUpdateDTO body) {
        return ResponseEntity.ok(accountService.patchAccount(accountId, body));
    }

    @PutMapping("/{accountId}/roles/{roleId}")
    private ResponseEntity<AccountResponseDTO> addRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.addRoleToAccount(accountId, roleId));
    }

    @DeleteMapping("/{accountId}/roles/{roleId}")
    private ResponseEntity<AccountResponseDTO> removeRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.removeRoleFromAccount(accountId, roleId));
    }

    @PutMapping("/{accountId}/app-users/{appUserId}")
    private ResponseEntity<AccountResponseDTO> addAppUser(@PathVariable Long accountId, @PathVariable Long appUserId) {
        return ResponseEntity.ok(accountService.addAppUserToAccount(accountId, appUserId));
    }

    @DeleteMapping("/{accountId}/app-users")
    private ResponseEntity<AccountResponseDTO> unassignAppUser(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.unassignAppUserFromAccount(accountId));
    }

    @GetMapping
    private ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/current")
    private ResponseEntity<AccountResponseDTO> getMyAccount(Authentication authentication) {
        return ResponseEntity.ok(accountService.getAccountByUsername(authentication.getName()));
    }

    @GetMapping("/username/{username}")
    private ResponseEntity<AccountResponseDTO> getAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }

    @GetMapping("/{accountId}")
    private ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @GetMapping("/{accountId}/roles")
    private ResponseEntity<List<RoleResponseDTO>> getAllRolesForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(roleService.getAllRolesByAccountId(accountId));
    }

    @GetMapping("/username/{username}/roles")
    private ResponseEntity<List<RoleResponseDTO>> getAllRolesForAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(roleService.getAllRolesByUsername(username));
    }

}
