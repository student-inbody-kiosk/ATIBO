package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        validateDuplicateAccount(account);
        return accountRepository.save(account);
    }

    private void validateDuplicateAccount(Account account) {
        Account findAccount = accountRepository.findByUsername(account.getUsername());
        if (findAccount != null) {
            throw new IllegalStateException("user with this username already exists.");
        }
    }
}
