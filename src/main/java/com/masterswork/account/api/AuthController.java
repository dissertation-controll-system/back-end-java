package com.masterswork.account.api;

import com.masterswork.account.api.dto.auth.LoginRequestDTO;
import com.masterswork.account.api.dto.auth.RefreshTokenRequestDTO;
import com.masterswork.account.api.dto.auth.SignUpRequestDTO;
import com.masterswork.account.api.dto.auth.TokensResponseDTO;
import com.masterswork.account.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @SecurityRequirements
    @PostMapping("/login")
    private ResponseEntity<TokensResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(
                authService.authenticateAndGenerateTokens(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @SecurityRequirements
    @PostMapping("/signup")
    private ResponseEntity<TokensResponseDTO> signup(@RequestBody SignUpRequestDTO signUpRequest) {
        return ResponseEntity.ok(authService.createUser(signUpRequest));
    }

    @SecurityRequirements
    @PostMapping("/token-refresh")
    private ResponseEntity<TokensResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshRequest) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshRequest.getRefreshToken()));
    }
}
