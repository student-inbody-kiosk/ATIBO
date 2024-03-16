package com.atibo.backendspring.accounts.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {


    @GetMapping("/api/school")
    public ResponseEntity<Model> test(Model model) {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();


        model.addAttribute("id", id);
        model.addAttribute("role", role);

        System.out.println("id:" + id);
        System.out.println("role" + role);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/api/admin")
    public ResponseEntity<String> admin() {

        return new ResponseEntity<>("test", HttpStatus.OK);
    }
//
//    @GetMapping("/api/school")
//    public ResponseEntity<String> test() {
//
//        return new ResponseEntity<>("test", HttpStatus.OK);
//    }
}
