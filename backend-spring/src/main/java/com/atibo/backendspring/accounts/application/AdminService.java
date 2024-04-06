package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AdminService {

    private final AccountRepository accountRepository;

    public AdminService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isWaiting() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        return account.isActive();
    }
}
