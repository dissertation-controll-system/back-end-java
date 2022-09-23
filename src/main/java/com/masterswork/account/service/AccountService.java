package com.masterswork.account.service;

import com.masterswork.account.api.dto.account.AccountResponseDTO;

import java.util.List;

public interface AccountService {

    AccountResponseDTO addRoleToAccount(Long accountId, Long roleId);

    AccountResponseDTO addAppUserToAccount(Long accountId, Long appUserId);

    List<AccountResponseDTO> getAllAccounts();

    AccountResponseDTO getAccountByUsername(String username);

    AccountResponseDTO getAccountById(Long id);

}
