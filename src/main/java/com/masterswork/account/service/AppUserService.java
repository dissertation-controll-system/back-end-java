package com.masterswork.account.service;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;

import java.util.Set;

public interface AppUserService {

    Set<String> getAllUserTypes();

    AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO);
}
