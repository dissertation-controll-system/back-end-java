package com.masterswork.account.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.masterswork.account.config.properties.JwtProperties;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private Algorithm algorithm;

    @PostConstruct
    void setAlgorithm() {
        this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
    }

    public String generateAccessToken(Account account) {
        return JWT.create()
                .withSubject(account.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getAccessTokenExpiry()))
                .withClaim("scp", account.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(Account account) {
        return JWT.create()
                .withSubject(account.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiry()))
                .sign(algorithm);
    }

    public Authentication getAuthenticationFromRawToken(String rawToken) {
        DecodedJWT decodedJWT = validateAndParseToken(rawToken);

        String username = decodedJWT.getSubject();
        Collection<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("scp").asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public DecodedJWT validateAndParseToken(String token) {
        return JWT.require(algorithm).build().verify(token);
    }

}
