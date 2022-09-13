package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.auth.SignUpRequestDTO;
import com.masterswork.account.api.dto.auth.TokensResponseDTO;
import com.masterswork.account.config.principal.UserPrincipalAdapter;
import com.masterswork.account.jwt.JwtUtil;
import com.masterswork.account.model.Account;
import com.masterswork.account.repository.AccountRepository;
import com.masterswork.account.service.AuthService;
import com.masterswork.account.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public TokensResponseDTO authenticateAndGenerateTokens(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new AuthenticationServiceException("Incorrect username or password", e);
        }

        final UserDetails account = userDetailsService.loadUserByUsername(username);
        return TokensResponseDTO.of(jwtUtil.generateAccessToken(account), jwtUtil.generateRefreshToken(account));
    }

    @Transactional
    @Override
    public TokensResponseDTO refreshAccessToken(String refreshToken) {
        String username = jwtUtil.validateAndParseToken(refreshToken).getSubject();

        final UserDetails account = userDetailsService.loadUserByUsername(username);
        return TokensResponseDTO.of(jwtUtil.generateAccessToken(account), refreshToken);
    }

    @Transactional
    @Override
    public TokensResponseDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        // TODO: implement user email verification... via email :)
        Account saved = accountRepository.save(accountMapper.createFrom(signUpRequestDTO));
        UserDetails account = new UserPrincipalAdapter(saved);

        return TokensResponseDTO.of(jwtUtil.generateAccessToken(account), jwtUtil.generateRefreshToken(account));
    }
}
