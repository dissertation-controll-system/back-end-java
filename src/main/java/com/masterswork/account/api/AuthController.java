package com.masterswork.account.api;

import com.masterswork.account.api.dto.auth.LoginRequestDTO;
import com.masterswork.account.api.dto.auth.RefreshTokenRequestDTO;
import com.masterswork.account.api.dto.auth.SignUpRequestDTO;
import com.masterswork.account.api.dto.auth.TokensResponseDTO;
import com.masterswork.account.api.dto.error.ApiError;
import com.masterswork.account.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@SecurityRequirements({})
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login user based on submitted credentials",
            description = "Consumes and validates user credentials, returns access token and refresh token on success.")
    @ApiResponse(responseCode = "200", description = "Successful authentication",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokensResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input provided",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "401", description = "Error during authentication",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "401", description = "Username corresponding to token data not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @PostMapping("/login")
    private ResponseEntity<TokensResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(
                authService.authenticateAndGenerateTokens(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @Operation(summary = "Create new user based on submitted data",
            description = "Consumes and validates username, email and password. Returns access token and refresh token on success.")
    @ApiResponse(responseCode = "200", description = "Successful registration",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokensResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input provided",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "Username or email already taken",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @PostMapping("/signup")
    private ResponseEntity<TokensResponseDTO> signup(@Valid @RequestBody SignUpRequestDTO signUpRequest) {
        return ResponseEntity.ok(authService.createUser(signUpRequest));
    }

    @Operation(summary = "Generates new jwt access token based on provided refresh token",
            description = "Consumes and validates refresh token, returns access token and refresh token on success.")
    @ApiResponse(responseCode = "200", description = "Successful refresh",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokensResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input provided",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "401", description = "Invalid refresh token",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "401", description = "Username corresponding to token data not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    @PostMapping("/token-refresh")
    private ResponseEntity<TokensResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshRequest) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshRequest.getRefreshToken()));
    }
}
