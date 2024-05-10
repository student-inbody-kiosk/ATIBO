package com.atibo.backendspring.accounts.jwt;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.application.CustomUserDetailService;
import com.atibo.backendspring.accounts.dto.CustomUserDetails;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final CustomUserDetailService customUserDetailService;

    public CustomAuthenticationProvider(AccountService accountService, CustomUserDetailService customUserDetailService) {
        this.accountService = accountService;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        CustomUserDetails details = (CustomUserDetails) customUserDetailService.loadUserByUsername(userName);

        if (accountService.isNotMatches(password, details.getPassword())) {
            System.out.println("비밀번호 불일치");
            throw new BadCredentialsException("비밀번호 틀렸습니다.");
        }
        return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
