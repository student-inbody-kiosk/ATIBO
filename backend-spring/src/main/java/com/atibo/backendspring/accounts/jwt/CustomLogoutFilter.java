package com.atibo.backendspring.accounts.jwt;

import java.io.IOException;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.accounts.repository.RefreshRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository, AccountRepository accountRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;

        this.accountRepository = accountRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //path and method verify
        String requestUri = request.getRequestURI();

        if (!requestUri.matches("^/api/accounts/logout/?$")) {

            filterChain.doFilter(request, response);

            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        //TODO 헤더에는 access 토큰이 들어옴, 이를 이용하여 db에 있는 refresh 토큰을 제거해야함 -> 어떻게 매핑을 할까?
        // auth 에서 username 얻어서 refresh 제거를 하는 방향으로 하면 될듯
        //get refresh token


//        String refresh = null;
//        refresh = request.getHeader("Authorization");

//        //refresh null check
//        if (refresh == null) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//        refresh.substring(7);
//
//        //expired check
//        try {
//            jwtUtil.isExpired(refresh);
//        } catch (ExpiredJwtException e) {
//
//            //response status code
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
//        String category = jwtUtil.getCategory(refresh);
//        if (!category.equals("refresh")) {
//            //response status code
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//        //DB에 저장되어 있는지 확인
//        Boolean isExist = refreshRepository.existsByRefresh(refresh);
//        if (!isExist) {
//            //response status code
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }

        //로그아웃 진행
        //Refresh 토큰 DB 에서 제거
        System.out.println("로그아웃필터");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);

        refreshRepository.deleteByAccount(account);

        response.setHeader("Content-type","application/json");
        response.setCharacterEncoding("utf-8");

        Response res = new Response("Logged out successfully");
        String result = objectMapper.writeValueAsString(res);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(result);
    }
}

