package com.atibo.backendspring.accounts.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;


public class CustomUserDetails implements UserDetails {

    private Account account;

    public CustomUserDetails(Account account) {

        this.account = account;
    }

    //UserDetail에서 기본 getter가 필요한 fields
//    private String username;                                    // 계정 id
//    private String password;
//    private boolean accountNonLocked = true;                    // 계정 잠금 여부
//    private boolean accountNonExpired = true;                   // 사용자 계정 만료 없음
//    private boolean credentialsNonExpired = true;               // 비밀번호 만료 없음
//    private boolean enabled = true;                             // 사용자 활성화 여부
//    private Collection<? extends GrantedAuthority> authorities; // 사용자 권한 목록
//
//    // 추가 설정
//    private String name;            // 사용자 이름
//    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

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
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //    public CustomUserDetails(AccountDto dto) {
//        this.username = dto.getUsername();
//        this.password = dto.getPassword();
//        this.email = dto.getEmail();
//        this.name = dto.getName();
//
//    }
}
