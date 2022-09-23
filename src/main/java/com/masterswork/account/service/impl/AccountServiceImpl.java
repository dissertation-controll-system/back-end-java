package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.Role;
import com.masterswork.account.repository.AccountRepository;
import com.masterswork.account.repository.AppUserRepository;
import com.masterswork.account.repository.RoleRepository;
import com.masterswork.account.service.AccountService;
import com.masterswork.account.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponseDTO addRoleToAccount(Long accountId, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("No role with id: " + roleId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("No account with id: " + accountId));

        account.addRole(role);

        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    public AccountResponseDTO addAppUserToAccount(Long accountId, Long appUserId) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + appUserId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("No account with id: " + accountId));

        account.setAppUser(appUser);

        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountMapper.toDto(accountRepository.findAll());
    }

    @Override
    public AccountResponseDTO getAccountByUsername(String username) {
        return accountRepository.findFirstByUsername(username)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No account with username: " + username));
    }

    @Override
    public AccountResponseDTO getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No account with id: " + accountId));
    }
}