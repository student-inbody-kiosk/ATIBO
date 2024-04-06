package com.atibo.backendspring.accounts.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.atibo.backendspring.accounts.domain.Account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class CustomUserDetails implements UserDetails {

    private final Account account;

    public CustomUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {                // 사용자 권한
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return account.getRole().toString();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    // 사용자 계정 만료 없음
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 계정 잠금 여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // 비밀번호 만료 없음
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (!account.isActive()) {
            System.out.println("승인대기중");
        }
        return account.isActive();
    }
}
