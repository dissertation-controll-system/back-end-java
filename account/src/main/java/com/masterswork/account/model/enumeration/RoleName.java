package com.masterswork.account.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleName {
    USER("USER"),
    ADMIN("ADMIN"),
    UNIT_HEAD("UNIT_HEAD"),
    UNIT_PARTICIPANT("UNIT_PARTICIPANT");

    private final String name;
}
