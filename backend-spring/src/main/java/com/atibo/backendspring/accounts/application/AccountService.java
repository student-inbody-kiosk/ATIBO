package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void saveAccount(AccountDto accountDto) {

        // db 중복 조회하기
        boolean isAccount = accountRepository.existsByUsername(accountDto.getUsername());
        if (isAccount) {
            throw new IllegalStateException("user with this username already exists");
        }

        Account data = new Account();

        data.setUsername(accountDto.getUsername());
        data.setName(accountDto.getName());
        data.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        data.setRole(AccountRole.ROLE_USER);
        data.setEmail(accountDto.getEmail());
        data.setComment(accountDto.getComment());

        accountRepository.save(data);
    }

}
