package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
public class AccountController {


//    private final AccountService accountService;
//
//    private final PasswordEncoder passwordEncoder;
//
//
//    @GetMapping(value = "new")
//    public String accountForm(Model model) {
//        model.addAttribute("accountDto", new AccountDto());
//        return "/";
//    }
//
//    @PostMapping(value = "new")
//    public String accountForm(@Valid AccountDto accountDto, BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            return "/";
//        }
//        try {
//            Account account = Account.createAccount(accountDto, passwordEncoder);
//            accountService.saveAccount(account);
//        } catch (IllegalStateException e) {
//            return "/";
//        }
//        return "redirect:/";
//    }


}
