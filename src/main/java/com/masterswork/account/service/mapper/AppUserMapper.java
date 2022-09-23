package com.masterswork.account.service.mapper;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "id", ignore = true)
    AppUser createFrom(AppUserCreateDTO source);

    @Mapping(target = "accountRef", expression = "java(mapAccountReference(appUser))")
    AppUserResponseDTO toDto(AppUser appUser);

    List<AppUserResponseDTO> toDto(Collection<AppUser> appUsers);

    default String mapAccountReference(AppUser appUser) {
        return Optional.ofNullable(appUser.getAccount())
                .map(account -> "/account/" + account.getId())
                .orElse(null);
    }

}
