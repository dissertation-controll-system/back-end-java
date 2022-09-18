package com.masterswork.account.api.dto.account;

import lombok.Data;

@Data
public class AccountResponseDTO {

    private Long id;

    private String username;

    private String email;

    private String appUserRef;

}
