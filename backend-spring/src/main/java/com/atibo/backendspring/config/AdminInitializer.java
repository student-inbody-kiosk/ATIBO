package com.atibo.backendspring.config;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements ApplicationRunner {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminInitializer(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Admin 계정 생성
        if (!accountRepository.existsByUsername("admin1")) {
            Account adminData = new Account("admin1", "관리자", "admin@c.com", bCryptPasswordEncoder.encode("1q2w3e4r!"), AccountRole.ROLE_ADMIN, "관리자 계정입니다.");
            adminData.changeActive();
            accountRepository.save(adminData);
        }
    }
}
