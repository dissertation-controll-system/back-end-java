package com.masterswork.account.config.principal;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserPrincipal implements Principal {

    private Long accountId;

    private Long appUserId;

    private String name;

    private Map<Integer, List<SimpleGrantedAuthority>> organizationRole;

}
