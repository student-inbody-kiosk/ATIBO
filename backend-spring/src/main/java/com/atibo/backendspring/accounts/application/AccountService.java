package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.ValidAccount;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }
    // 회원가입
    public AccountDto.ResponseDto saveAccount(AccountDto.RequestDto requestDto) {
        //
        ValidAccount.validAccountDto(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword());
        boolean isExist = accountRepository.existsByUsername(requestDto.getUsername());
        if (isExist) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user with this username already exists",
                    new IllegalArgumentException()
            );
        }
        Account data = new Account();
        data.setUsername(requestDto.getUsername());
        data.setName(requestDto.getName());
        data.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        data.setRole(AccountRole.ROLE_USER);
        data.setEmail(requestDto.getEmail());
        data.setComment(requestDto.getComment());
        Account account = accountRepository.save(data);

        return new AccountDto.ResponseDto().toResponseDto(account);
    }

    public boolean existByUserName(String username) {

        return accountRepository.existsByUsername(username);
    }
}
