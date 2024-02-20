package com.atibo.backendspring.accounts.service;


import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class AccountServiceTest {

    @Autowired
    AccountService accountService;
    PasswordEncoder passwordEncoder;

    public Account createAccount() {
        AccountDto accountDto = AccountDto.builder()
                .username("test123")
                .name("최성빈")
                .email("test@email.com")
                .comment("test11")
                .password("123123123")
                .build();
        System.out.println("회원생성" );
        return Account.createAccount(accountDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입테스트")
    public void saveAccountTest() {
        Account account = createAccount();

        accountService.saveAccount(account);
        System.out.println("회원 저장");
//        assertEquals(account.getUsername(), savedAccount.getUsername());
    }

}

