package com.atibo.backendspring.accounts.domain;

import java.util.List;
import java.util.stream.Collectors;


public class Accounts {
    private final List<Account> accounts;

    public Accounts(List<Account> accounts) {
        this.accounts = excludeAdmin(accounts);
    }

    private List<Account> excludeAdmin(List<Account> accounts) {
        return accounts.stream()
                       .filter(account -> account.getRole().equals(AccountRole.ROLE_STUDENT))
                       .collect(Collectors.toList());
    }

    public List<Account> activeAccounts() {
        return accounts.stream()
                       .filter(Account::isActive)
                       .collect(Collectors.toList());
    }

    public List<Account> inActiveAccounts() {
        return accounts.stream()
                       .filter(account -> !account.isActive())
                       .collect(Collectors.toList());
    }
}
