package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.dto.AccountDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    @PostMapping("/api/accounts/login/")
    public ResponseEntity<AccountDto.LoginRequestDto> login(@RequestBody AccountDto.LoginRequestDto loginRequestDto) {
//      로그인  테스트
        System.out.println("login 시도");
        return new ResponseEntity<>(loginRequestDto, HttpStatus.OK);
    }
}
