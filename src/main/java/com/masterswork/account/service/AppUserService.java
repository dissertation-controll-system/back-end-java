package com.masterswork.account.service;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;

import java.util.List;
import java.util.Set;

public interface AppUserService {

    AppUserResponseDTO assignCathedra(Long userId, Long cathedraId);

    AppUserResponseDTO unassignCathedra(Long userId, Long cathedraId);

    AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO);

    AppUserResponseDTO updateUser(Long userId, AppUserUpdateDTO appUserUpdateDTO);

    AppUserResponseDTO patchUser(Long userId, AppUserUpdateDTO appUserUpdateDTO);

    List<AppUserResponseDTO> getAllAppUsers();

    Set<String> getAllUserTypes();
}
