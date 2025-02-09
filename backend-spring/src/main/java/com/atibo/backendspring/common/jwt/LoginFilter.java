package com.atibo.backendspring.common.jwt;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.RefreshToken;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.accounts.repository.RefreshRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final AccountRepository accountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(CustomAuthenticationProvider customAuthenticationProvider, JWTUtil jwtUtil, RefreshRepository refreshRepository, AccountRepository accountRepository) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.accountRepository = accountRepository;
    }

    //로그인 요청시 가장 먼저 실행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {

        System.out.println("====================로그인 시도===================");
        // json -> java 변환
        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String messageBody = null;
        try {
            messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AccountDto.LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = objectMapper.readValue(messageBody, AccountDto.LoginRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //클라이언트 요청 Username, password 추출
        String username = loginRequestDto.username;
        String password = loginRequestDto.password;
        System.out.println("username:" + username);
        System.out.println("password:" + password);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야함
        //TODO: role 값 설정(임시로 null 처리함)
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        System.out.println("======= Authentic =======");
        System.out.println(authToken);
        return customAuthenticationProvider.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (JWT 발급), spring security 에서 자동실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        System.out.println("======= 로그인 성공 =======");
        //유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        System.out.println("======= 토큰 생성 =======");
        String access = jwtUtil.createJwt("access", username, role, 9999600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L);

        AccountDto.LoginResponseDto tokens = AccountDto.LoginResponseDto.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        String res = objectMapper.writeValueAsString(tokens);
        System.out.println(">>>>>>>>>>>> tokens:" + res);
        response.getWriter().print(res);
        System.out.println(auth.getAuthority());
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
        System.out.println("======= login 실패 =======");
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Account account = accountRepository.findByUsername(username);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccount(account);
        refreshToken.setRefresh(refresh);
        refreshToken.setExpiration(date.toString());
        refreshRepository.deleteByAccount(account);
        refreshRepository.save(refreshToken);
    }
}
