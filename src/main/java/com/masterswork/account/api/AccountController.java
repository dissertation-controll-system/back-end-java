package com.masterswork.account.api;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.service.AccountService;
import com.masterswork.account.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final RoleService roleService;

    @PutMapping ("/{accountId}/roles/{roleId}")
    private ResponseEntity<AccountResponseDTO> addRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.addRoleToAccount(accountId, roleId));
    }

    @PutMapping("/{accountId}/app-users/{appUserId}")
    private ResponseEntity<AccountResponseDTO> addAppUser(@PathVariable Long accountId, @PathVariable Long appUserId) {
        return ResponseEntity.ok(accountService.addAppUserToAccount(accountId, appUserId));
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
