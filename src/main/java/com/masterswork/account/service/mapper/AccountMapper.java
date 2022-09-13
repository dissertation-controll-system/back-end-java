package com.masterswork.account.service.mapper;

import com.masterswork.account.api.dto.auth.SignUpRequestDTO;
import com.masterswork.account.model.Account;
import com.masterswork.account.service.mapper.qualifier.PasswordEncoded;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountMapperUtils.class)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedBy = PasswordEncoded.class)
    Account createFrom(SignUpRequestDTO source);
}
