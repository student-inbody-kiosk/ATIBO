package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import java.util.Objects;



@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ValidAccount validAccount;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, ValidAccount validAccount, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.validAccount = validAccount;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public AccountDto.ResponseDto saveAccount(AccountDto.RequestDto requestDto) {

        existByUserName(requestDto.getUsername());
        existByEmail(requestDto.getEmail());
        ValidAccount.validAccount(requestDto);

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

    // email 변경
    public void changeEmail(String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        ValidAccount.validEmail(email);
        account.changeEmail(email);
    }

    public boolean isNotMatches(String password, String encodePassword) {
        return !bCryptPasswordEncoder.matches(password, encodePassword);
    }

    public void changePassword(AccountDto.changePasswordDto passwordDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findByUsername(auth.getName());
        String encodedPwd = account.getPassword();
        String requestPwd = passwordDto.getOldPassword();
        String newPwd = bCryptPasswordEncoder.encode(requestPwd);
        if (isNotMatches(requestPwd, encodedPwd)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 틀렸습니다.",
                    new IllegalArgumentException()
            );
        }
        ValidAccount.validPassword(passwordDto.getNewPassword());
        if (!Objects.equals(passwordDto.getNewPassword(), passwordDto.getConfirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 일치하지 않습니다.",
                    new IllegalArgumentException()
            );
        }
        account.changePassword(newPwd);
    }

    public void existByUserName(String username) {

        boolean isExist = accountRepository.existsByUsername(username);

        if (isExist) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user with this username already exists",
                    new IllegalArgumentException()
            );
        }
    }

    public void existByUserName1(String username) {

        boolean isExist = accountRepository.existsByUsername(username);

        if (!isExist) {
            System.out.println("잠깐, 너같은놈 없는데?");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Check username",
                    new IllegalArgumentException()
            );
        }
    }

    public void existByEmail(String email) {
        boolean isExist = accountRepository.existsByEmail(email);
        if (isExist) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user with this email already exists",
                    new IllegalArgumentException()
            );
        }
    }
}
