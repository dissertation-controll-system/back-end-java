package com.masterswork.account.api;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.api.dto.account.AccountUpdateDTO;
import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.service.AccountService;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.RoleService;
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

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AppUserService appUserService;
    private final RoleService roleService;

    @Operation(summary = "Create new appUser and assign to account")
    @PostMapping(path = "/{accountId}/app-users", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AppUserResponseDTO> createAppUserForAccount(@PathVariable Long accountId, @Valid @RequestBody AppUserCreateDTO body) {
        return ResponseEntity.ok(appUserService.createAppUserForAccount(accountId, body));
    }

    @Operation(summary = "Update account by accountId")
    @PutMapping(path = "/{accountId}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountId, @Valid @RequestBody AccountUpdateDTO body) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, body));
    }

    @Operation(summary = "Patch account by accountId")
    @PatchMapping(path = "/{accountId}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> patchAccount(@PathVariable Long accountId, @RequestBody AccountUpdateDTO body) {
        return ResponseEntity.ok(accountService.patchAccount(accountId, body));
    }

    @Operation(summary = "Add role to account")
    @PutMapping(path = "/{accountId}/roles/{roleId}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> addRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.addRoleToAccount(accountId, roleId));
    }

    @Operation(summary = "Remove role from account")
    @DeleteMapping(path = "/{accountId}/roles/{roleId}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> removeRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.removeRoleFromAccount(accountId, roleId));
    }

    @Operation(summary = "Assign appUser to account")
    @PutMapping(path = "/{accountId}/app-users/{appUserId}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> addAppUser(@PathVariable Long accountId, @PathVariable Long appUserId) {
        return ResponseEntity.ok(accountService.addAppUserToAccount(accountId, appUserId));
    }

    @Operation(summary = "Unassign appUser from account")
    @DeleteMapping(path = "/{accountId}/app-users", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> unassignAppUser(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.unassignAppUserFromAccount(accountId));
    }

    @Operation(summary = "Get all accounts")
    @GetMapping(consumes = "application/json", produces = "application/json")
    private ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Operation(summary = "Get account of the current user")
    @GetMapping(path = "/current", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> getMyAccount(Authentication authentication) {
        return ResponseEntity.ok(accountService.getAccountByUsername(authentication.getName()));
    }

    @Operation(summary = "Get account by username")
    @GetMapping(path = "/username/{username}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> getAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }

    @Operation(summary = "Get account by accountId")
    @GetMapping(path = "/{accountId}", consumes = "application/json", produces = "application/json")
    private ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @Operation(summary = "Get roles assigned to account by accountId")
    @GetMapping(path = "/{accountId}/roles", consumes = "application/json", produces = "application/json")
    private ResponseEntity<List<RoleResponseDTO>> getAllRolesForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(roleService.getAllRolesByAccountId(accountId));
    }

    @Operation(summary = "Get roles assigned to account by username")
    @GetMapping(path = "/username/{username}/roles", consumes = "application/json", produces = "application/json")
    private ResponseEntity<List<RoleResponseDTO>> getAllRolesForAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(roleService.getAllRolesByUsername(username));
    }

}
