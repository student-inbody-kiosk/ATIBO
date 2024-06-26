package com.atibo.backendspring.accounts.application;

import java.sql.SQLOutput;
import java.util.List;
import java.util.UUID;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.Accounts;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.util.JsonUtil;

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
    private ValidAccount validAccount;

    public AdminService(AccountRepository accountRepository, ValidAccount validAccount) {
        this.accountRepository = accountRepository;
        this.validAccount = validAccount;
    }

    public boolean isWaiting() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);

        return account.isActive();
    }

    public AccountDto.accountDetail approveAccount(UUID userId) {
        validAccount.existedUserId(userId);
        Account account = accountRepository.findById(userId);
        account.changeActive();
        System.out.println("계정 승인: " + account.getUsername());

        return new AccountDto.accountDetail().toAccountDto(account);
    }

    public void deleteAccount(UUID userId) {
        validAccount.existedUserId(userId);
        System.out.println("계정 삭제");
        accountRepository.deleteById(userId);
    }

    public String getAccounts() {
        Accounts accounts = new Accounts(accountRepository.findAll());
        List<Account> inActives = accounts.inActiveAccounts();
        List<Account> actives = accounts.activeAccounts();
        return JsonUtil.listsToJson(actives, inActives);
    }
}
