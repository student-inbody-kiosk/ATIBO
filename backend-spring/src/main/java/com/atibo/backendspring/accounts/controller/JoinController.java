package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.dto.AccountDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class JoinController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public String createAccounts(@RequestBody AccountDto accountDto) {

        accountService.saveAccount(accountDto);

        return "redirect:/";
    }
}
