package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.application.EmailService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.accounts.jwt.JWTUtil;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.accounts.repository.RefreshRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.ExpiredJwtException;


@Controller
@ResponseBody
public class AccountController {

    private final JWTUtil jwtUtil;
    private final AccountRepository accountRepository;
    private final RefreshRepository refreshRepository;
    private final AccountService accountService;
    private final EmailService emailService;


    public AccountController(JWTUtil jwtUtil, AccountRepository accountRepository, AccountService accountService, RefreshRepository refreshRepository, EmailService emailService    ) {

        this.jwtUtil = jwtUtil;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.refreshRepository = refreshRepository;
        this.emailService = emailService;
    }

    @GetMapping("/api/accounts/")
    public ResponseEntity<?> getUserInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("회원정보조회");
        Account account = accountRepository.findByUsername(username);
        AccountDto.ResponseDto userInfo = new AccountDto.ResponseDto().toResponseDto(account);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @DeleteMapping("/api/accounts/")
    public ResponseEntity<?> deleteAccount() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("회원삭제");
        Account account = accountRepository.findByUsername(username);
        refreshRepository.deleteByAccount(account);
        accountRepository.deleteByUsername(username);
        Response response = new Response("The user is successfully deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/accounts/token/refresh/")
    public ResponseEntity<?> reissue(@RequestBody AccountDto.tokenDto request) {

        //get refresh token
        String refresh = request.getRefreshToken();
        // TODO: username 이 잘못되었을 경우 에러처리 확인하고 만들기
        String username = request.getUsername();
        System.out.println(username);
        System.out.println(refresh);
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

        System.out.println("토큰 생성한다");

        //make new JWT
        String accessToken = jwtUtil.createJwt("access", username, role, 600000L);
        //DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장 (Refresh Rotate 기능 고려해보기)
        //response
        AccountDto.reissueDto newAccess = new AccountDto.reissueDto(accessToken);
        return new ResponseEntity<>(newAccess, HttpStatus.OK);
    }

    @PutMapping("/api/accounts/password/reset/")
    public ResponseEntity<?> passwordReset(@RequestBody AccountDto.resetPasswordDto request) {
        emailService.setMail(request);
        Response response = new Response("A new password has been sent to your email");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/accounts/email/change/")
    public ResponseEntity<?> changeEmail(@RequestBody AccountDto.changeEmailDto emailDto) {
        String email = emailDto.getEmail();
        accountService.changeEmail(email);
        Response.emailResponse emailResponse = new Response.emailResponse(email);
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }
}

