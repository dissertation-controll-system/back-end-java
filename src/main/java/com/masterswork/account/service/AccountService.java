package com.masterswork.account.service;

import com.masterswork.account.api.dto.account.AccountResponseDTO;

public interface AccountService {

    AccountResponseDTO addRoleToAccount(Long accountId, Long roleId);

    AccountResponseDTO addAppUserToAccount(Long accountId, Long appUserId);

}
