package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.Cathedra;
import com.masterswork.account.model.enumeration.PersonType;
import com.masterswork.account.repository.AppUserRepository;
import com.masterswork.account.repository.CathedraRepository;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final CathedraRepository cathedraRepository;
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
    public AppUserResponseDTO unassignCathedra(Long userId, Long cathedraId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id: " + cathedraId));

        appUser.removeCathedra(cathedra);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO) {
        var newEntity = appUserMapper.createFrom(appUserCreateDTO);
        return appUserMapper.toDto(appUserRepository.save(newEntity));
    }

    @Override
    public AppUserResponseDTO updateUser(Long userId, AppUserUpdateDTO appUserUpdateDTO) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));

        appUserMapper.updateFrom(appUser, appUserUpdateDTO);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO patchUser(Long userId, AppUserUpdateDTO appUserUpdateDTO) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));

        appUserMapper.patchFrom(appUser, appUserUpdateDTO);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO getUserById(Long userId) {
        return appUserRepository.findById(userId)
                .map(appUserMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));
    }

    @Override
    public AppUserResponseDTO getUserByAccountUsername(String username) {
        return appUserRepository.findByAccount_Username(username)
                .map(appUserMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No appUser associated with username: " + username));
    }

    @Override
    public List<AppUserResponseDTO> getAllAppUsers() {
        return appUserMapper.toDto(appUserRepository.findAll());
    }

    @Override
    public Set<String> getAllUserTypes() {
        return PersonType.getAllTypesNames();
    }

    @Override
    public void deleteAppUserById(Long userId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));

        appUserRepository.delete(appUser);
    }
}
