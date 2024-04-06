package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AdminService;
import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {

    private final AdminService adminService;
    private final AccountRepository accountRepository;

    public AdminController(AdminService adminService, AccountRepository accountRepository) {
        this.adminService = adminService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/api/accounts/admin/waiting/")
    public ResponseEntity<?> waitAccounts() {
        boolean isWaiting = adminService.isWaiting();
        Response.isActiveResponse response = new Response.isActiveResponse(isWaiting);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
