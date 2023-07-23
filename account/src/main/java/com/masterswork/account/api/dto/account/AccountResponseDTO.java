package com.masterswork.account.api.dto.account;

import com.masterswork.account.model.enumeration.EmailActivationState;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class AccountResponseDTO {

    private Long id;

    private String username;

    private String email;

    private Boolean active;

    private EmailActivationState emailActivationState;

    private Set<String> roles;

    private Map<Long, Set<String>> organizationRoles;

    private String appUserRef;

}
