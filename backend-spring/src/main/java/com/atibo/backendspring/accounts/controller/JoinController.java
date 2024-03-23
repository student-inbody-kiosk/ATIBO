package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.application.ValidAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletOutputStream;

@RestController
public class JoinController {

    private final AccountService accountService;

    @Autowired
    public JoinController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/accounts/")
    public ResponseEntity<AccountDto.ResponseDto> createAccounts(@RequestBody AccountDto.RequestDto requestDto) {
        System.out.println("가입하고싶어욤");
        AccountDto.ResponseDto responseDto = accountService.saveAccount(requestDto);
        System.out.println("가입했더욤");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/api/accounts/username/check/")
    public ResponseEntity<?> DuplicationUserName(@RequestBody AccountDto.checkUserNameDto request) {
        String username = request.getUsername();
        accountService.existByUserName(username);
        ValidAccount.validUserName(username);

        AccountDto.checkUserNameResponse response = new AccountDto.checkUserNameResponse();

        return new ResponseEntity<>(response, HttpStatus.OK);
    };

}
