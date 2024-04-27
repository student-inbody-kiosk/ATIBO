package com.atibo.backendspring.accounts.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {
    ROLE_USER("user"), ROLE_ADMIN("admin"), ROLE_STUDENT("student");

    private String role;

    private AccountRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
