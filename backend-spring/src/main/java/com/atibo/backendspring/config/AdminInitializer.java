package com.atibo.backendspring.config;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements ApplicationRunner {
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AdminInitializer(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Admin 계정 생성
        AccountDto.RequestDto adminData = new AccountDto.RequestDto(
                "admin1", "admin1", "1q2w3e4r!", "admin@c.com", "관리자 계정입니다."
        );
        accountService.saveAccount(adminData);
        Account admin = accountRepository.findByUsername("admin1");
        admin.changeActive();
        admin.changeRole(AccountRole.ROLE_ADMIN);
        accountRepository.save(admin);
    }
}
