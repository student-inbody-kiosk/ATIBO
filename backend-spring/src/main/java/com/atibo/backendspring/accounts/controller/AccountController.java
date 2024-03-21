package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.jwt.JWTUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.ExpiredJwtException;


@Controller
@ResponseBody
public class AccountController {

    private final JWTUtil jwtUtil;

    private final AccountService accountService;


    public AccountController(JWTUtil jwtUtil, AccountService accountService) {

        this.jwtUtil = jwtUtil;
        this.accountService = accountService;

    }

    @PostMapping("/api/accounts/token/refresh/")
    public ResponseEntity<?> reissue(@RequestBody AccountDto.tokenDto request) {

        //get refresh token
        String refresh = request.getRefreshToken();

        // TODO: username 이 잘못되었을 경우 에러처리 확인하고 만들기
        String username = request.getUsername();

        if (accountService.existByUserName(username) == false) {

            return new ResponseEntity<>("Check username", HttpStatus.NOT_FOUND);
        }

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
//        String category = jwtUtil.getCategory(refresh);
//
//        if (!category.equals("refresh")) {
//
//            //response status code
//            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
//        }

        username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String accessToken = jwtUtil.createJwt("access", username, role, 600000L);

        AccountDto.reissueDto new_accessToken = new AccountDto.reissueDto(accessToken);

        return new ResponseEntity<>(new_accessToken, HttpStatus.OK);

    }
}

