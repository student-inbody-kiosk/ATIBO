package com.atibo.backendspring.accounts.controller;

import java.util.Date;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.RefreshEntity;
import com.atibo.backendspring.accounts.jwt.JWTUtil;
import com.atibo.backendspring.accounts.repository.RefreshRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.ExpiredJwtException;


@Controller
@ResponseBody
public class AccountController {

    private final JWTUtil jwtUtil;

    private final RefreshRepository refreshRepository;
    private final AccountService accountService;


    public AccountController(JWTUtil jwtUtil, AccountService accountService, RefreshRepository refreshRepository) {

        this.jwtUtil = jwtUtil;
        this.accountService = accountService;
        this.refreshRepository = refreshRepository;

    }

    @PostMapping("/api/accounts/token/refresh/")
    public ResponseEntity<?> reissue(@RequestBody AccountDto.tokenDto request) {

        //get refresh token
        String refresh = request.getRefreshToken();

        // TODO: username 이 잘못되었을 경우 에러처리 확인하고 만들기
        String username = request.getUsername();

        accountService.existByUserName1(username);

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

        //토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String accessToken = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 8640000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        //TODO: 구현 물어봐야함, 초기화 안할꺼면 없어져야하는 로직
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        AccountDto.reissueDto newAccess = new AccountDto.reissueDto(accessToken);

        return new ResponseEntity<>(newAccess, HttpStatus.OK);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

}

