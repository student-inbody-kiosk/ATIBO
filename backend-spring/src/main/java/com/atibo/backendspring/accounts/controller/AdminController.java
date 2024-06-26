package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AdminService;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/api/accounts/admin/")
    public ResponseEntity<?> allAccount() {
        String accounts = adminService.getAccounts();

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PutMapping("/api/accounts/admin/{userId}/")
    public ResponseEntity<?> approveAccount(@PathVariable("userId") UUID userId) {
        AccountDto.accountDetail response = adminService.approveAccount(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/accounts/admin/{userId}/")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") UUID userId) {
        adminService.deleteAccount(userId);
        Response response = new Response("Delete Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/api/school/")
//    public ResponseEntity<?> School() {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
