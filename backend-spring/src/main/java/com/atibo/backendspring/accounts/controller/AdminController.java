package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AdminService;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/api/accounts/admin/waiting/")
    public ResponseEntity<?> waitAccount() {
        boolean isWaiting = adminService.isWaiting();
        Response.isActiveResponse response = new Response.isActiveResponse(isWaiting);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/accounts/admin/{userId}/")
    public ResponseEntity<?> approveAccount(@PathVariable("userId") String userId) {
        AccountDto.accountDetail response = adminService.approveAccount(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
