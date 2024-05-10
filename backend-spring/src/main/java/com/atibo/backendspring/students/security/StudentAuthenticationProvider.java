package com.atibo.backendspring.students.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class StudentAuthenticationProvider implements AuthenticationProvider {

    private final StudentDetailsService studentDetailsService;

    public StudentAuthenticationProvider(StudentDetailsService studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UUID id = UUID.fromString(authentication.getName());
        String password = (String) authentication.getCredentials();
        StudentDetails details = studentDetailsService.loadUserById(id);

        if (!Objects.equals(password, details.getPassword())) {
            System.out.println("[학생] 비밀번호 불일치");
            throw new BadCredentialsException("비밀번호 틀렸습니다.");
        }
        return new UsernamePasswordAuthenticationToken(details.getUserId(), null, details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
