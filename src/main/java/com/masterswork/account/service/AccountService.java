package com.masterswork.account.service;

import com.masterswork.account.api.dto.account.AccountResponseDTO;

import java.util.List;

public interface AccountService {

    AccountResponseDTO addRoleToAccount(Long accountId, Long roleId);

    AccountResponseDTO removeRoleFromAccount(Long accountId, Long roleId);

    AccountResponseDTO addAppUserToAccount(Long accountId, Long appUserId);

    AccountResponseDTO unassignAppUserFromAccount(Long accountId);

    List<AccountResponseDTO> getAllAccounts();

    AccountResponseDTO getAccountByUsername(String username);

    AccountResponseDTO getAccountById(Long id);

}
