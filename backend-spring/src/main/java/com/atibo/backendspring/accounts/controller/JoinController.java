package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.val;

@Controller
public class JoinController {

    private final AccountService accountService;

    @Autowired
    public JoinController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/accounts")
    public ResponseEntity<AccountDto.ResponseDto> createAccounts(@RequestBody AccountDto.RequestDto requestDto) {
        AccountDto.ResponseDto responseDto = accountService.saveAccount(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
