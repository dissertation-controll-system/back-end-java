package com.masterswork.account.service;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;

import java.util.List;
import java.util.Set;

public interface AppUserService {

    AppUserResponseDTO assignCathedra(Long userId, Long cathedraId);

    AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO);

    List<AppUserResponseDTO> getAllAppUsers();

    Set<String> getAllUserTypes();
}
