package com.masterswork.account.service;

import com.masterswork.account.api.dto.auth.SignUpRequestDTO;
import com.masterswork.account.api.dto.auth.TokensResponseDTO;


public interface AuthService {

    TokensResponseDTO authenticateAndGenerateTokens(String username, String password);

    TokensResponseDTO refreshAccessToken(String refreshToken);

    TokensResponseDTO createUser(SignUpRequestDTO signUpRequestDTO);
}
