package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.dto.AccountDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class JoinController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/api/accounts")
    public ResponseEntity<AccountDto> createAccounts(@RequestBody AccountDto accountDto) {

        accountService.saveAccount(accountDto);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }
}
