package com.masterswork.account.api.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
