package com.atibo.backendspring.students.security;

import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.students.domain.Student;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class StudentDetails implements UserDetails {

    private final Student student;

    public StudentDetails(Student student) {
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return AccountRole.ROLE_STUDENT.name();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getId().toString();
    }

    public UUID getUserId() {
        return student.getId();
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
        return true;
    }
}
