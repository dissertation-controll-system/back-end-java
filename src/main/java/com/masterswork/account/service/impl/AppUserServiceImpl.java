package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.model.enumeration.PersonType;
import com.masterswork.account.repository.AppUserRepository;
import com.masterswork.account.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public Set<String> getAllUserTypes() {
        return PersonType.getAllTypesNames();
    }

    @Override
    public AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO) {
        return null;
    }
}
