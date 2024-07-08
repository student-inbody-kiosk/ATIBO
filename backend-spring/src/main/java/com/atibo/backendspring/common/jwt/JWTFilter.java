package com.atibo.backendspring.common.jwt;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.atibo.backendspring.accounts.dto.CustomUserDetails;
import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.security.StudentDetails;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final StudentLoginFilter studentLoginFilter;
    private final LoginFilter loginFilter;

    public JWTFilter(JWTUtil jwtUtil, StudentLoginFilter studentLoginFilter, LoginFilter loginFilter) {

        this.jwtUtil = jwtUtil;
        this.studentLoginFilter = studentLoginFilter;
        this.loginFilter = loginFilter;
    }
    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("토큰췍쿠첵");
        // 헤더에서 access 키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("Authorization");
        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        accessToken = accessToken.substring(7);

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // username, role 값을 획득
        String role = jwtUtil.getRole(accessToken);
        String username = jwtUtil.getUsername(accessToken);
        System.out.println(username);
        //TODO: role 값이 학생이면 로직 다르게 타야할듯
        if (Objects.equals(role, "ROLE_STUDENT")) {
            System.out.println("학생 토큰 정보");
            System.out.println(role);
            Student student = new Student();
            student.setId(username);
            StudentDetails studentDetails = new StudentDetails(student);
            Authentication authToken = new UsernamePasswordAuthenticationToken(studentDetails, null, studentDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("학생 컨텍스트홀저장");
            studentLoginFilter.doFilter(request, response, filterChain);
        } else {
            Account account = new Account();
            account.setUsername(username);
            account.setRole(AccountRole.valueOf(role));
            CustomUserDetails customUserDetails = new CustomUserDetails(account);
            System.out.println("====요청 토큰 정보====");
            System.out.println("username: " + username);
            System.out.println("role: " + AccountRole.valueOf(role));
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("잘됨");
            loginFilter.doFilter(request, response, filterChain);
        }
    }
}
