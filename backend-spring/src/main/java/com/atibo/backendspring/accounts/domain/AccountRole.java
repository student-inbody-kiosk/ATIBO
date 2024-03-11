package com.atibo.backendspring.accounts.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {
    ROLE_USER, ROLE_ADMIN
}
