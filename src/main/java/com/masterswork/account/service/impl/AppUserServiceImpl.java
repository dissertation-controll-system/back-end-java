package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.Cathedra;
import com.masterswork.account.model.enumeration.PersonType;
import com.masterswork.account.repository.AccountRepository;
import com.masterswork.account.repository.AppUserRepository;
import com.masterswork.account.repository.CathedraRepository;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final CathedraRepository cathedraRepository;
    private final AccountRepository accountRepository;
    private final AppUserMapper appUserMapper;

    @Override
    public AppUserResponseDTO assignCathedra(Long userId, Long cathedraId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id: " + cathedraId));

        appUser.addCathedra(cathedra);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO) {
        var newEntity = appUserMapper.createFrom(appUserCreateDTO);

        if (appUserCreateDTO.getAccountId() != null) updateAccountById(appUserCreateDTO.getAccountId(), newEntity);

        return appUserMapper.toDto(appUserRepository.save(newEntity));
    }

    @Override
    public List<AppUserResponseDTO> getAllAppUsers() {
        return appUserMapper.toDto(appUserRepository.findAll());
    }

    @Override
    public Set<String> getAllUserTypes() {
        return PersonType.getAllTypesNames();
    }

    private void updateAccountById(Long accountId, AppUser appUser) {
        if (appUser.getAccount() == null ||
                !ObjectUtils.nullSafeEquals(accountId, appUser.getAccount().getId())) {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new EntityNotFoundException("No account with id: " + accountId));

            appUser.setAccount(account);
        }
    }
}
