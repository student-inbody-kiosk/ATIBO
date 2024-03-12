package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.jwt.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.ExpiredJwtException;


@Controller
@ResponseBody
public class AccountController {

    private final JWTUtil jwtUtil;

    public AccountController(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/accounts/token/refresh/")
    public ResponseEntity<?> reissue(@RequestBody AccountDto.tokenDto request) {

        //get refresh token
        String refresh = request.getRefreshToken();

        // TODO: username 이 잘못되었을 경우 에러처리 확인하고 만들기
        // String name = request.getUsername();

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("You may be logged in from another place with that ID", HttpStatus.UNAUTHORIZED);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("Your session in terminated", HttpStatus.UNAUTHORIZED);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시), 아마 필요없을듯 api 명세에 따르면
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String accessToken = jwtUtil.createJwt("access", username, role, 600000L);

        //response

        return ResponseEntity.ok().body(accessToken);
    }
}

