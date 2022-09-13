package com.masterswork.account.config;

import com.masterswork.account.config.principal.UserPrincipalAdapter;
import com.masterswork.account.jwt.JwtUtil;
import com.masterswork.account.jwt.filter.JwtAuthorizationFilter;
import com.masterswork.account.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityConfig {

    private final static String[] swaggerEndpoints = {
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/swagger-ui.html/**"
    };

    private final static String loginEndpoint = "/login";
    private final static String signUpEndpoint = "/signup";
    private final static String refreshTokenEndpoint = "/token-refresh";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers(loginEndpoint, signUpEndpoint, refreshTokenEndpoint).permitAll()
                        .antMatchers(swaggerEndpoints).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil, getAllWhitelistEndpoints()), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(AccountRepository userRepository) {
        return username -> userRepository.findFirstByUsername(username)
                .map(UserPrincipalAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Set<String> getAllWhitelistEndpoints() {
        Set<String> whitelist = new HashSet<>(Arrays.asList(swaggerEndpoints));
        whitelist.add(loginEndpoint);
        whitelist.add(signUpEndpoint);
        whitelist.add(refreshTokenEndpoint);
        return whitelist;
    }
}
