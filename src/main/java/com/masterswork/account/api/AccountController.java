package com.masterswork.account.api;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PutMapping ("/{accountId}/roles/{roleId}")
    private ResponseEntity<AccountResponseDTO> addRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.addRoleToAccount(accountId, roleId));
    }

    @PutMapping("/{accountId}/app-users/{appUserId}")
    private ResponseEntity<AccountResponseDTO> addAppUser(@PathVariable Long accountId, @PathVariable Long appUserId) {
        return ResponseEntity.ok(accountService.addAppUserToAccount(accountId, appUserId));
    }

}
