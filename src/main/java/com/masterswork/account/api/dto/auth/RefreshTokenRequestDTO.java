package com.masterswork.account.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RefreshTokenRequestDTO {

    @JsonProperty("refresh_token")
    private String refreshToken;
}
