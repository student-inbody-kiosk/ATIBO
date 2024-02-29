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

}
