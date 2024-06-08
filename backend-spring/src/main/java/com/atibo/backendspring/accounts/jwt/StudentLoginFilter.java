package com.atibo.backendspring.accounts.jwt;

import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.dto.StudentDto;
import com.atibo.backendspring.students.repository.StudentRepository;
import com.atibo.backendspring.students.security.StudentAuthenticationProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class StudentLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final StudentAuthenticationProvider studentAuthenticationProvider;
    private final JWTUtil jwtUtil;
    private final StudentRepository studentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StudentLoginFilter(StudentAuthenticationProvider studentAuthenticationProvider, JWTUtil jwtUtil, StudentRepository studentRepository) {

        this.studentAuthenticationProvider = studentAuthenticationProvider;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
    }

    //로그인 요청시 가장 먼저 실행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {

        System.out.println("====================학생 로그인 시도===================");
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
        StudentDto.StudentLoginRequest loginRequestDto = null;
        try {
            loginRequestDto = objectMapper.readValue(messageBody, StudentDto.StudentLoginRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 요청 URI 가져오기
        String requestURI = request.getRequestURI();
        System.out.println("Requested URI: " + requestURI);

        // URI 분석하여 경로 변수 추출
        String[] pathParts = requestURI.split("/");
        // 예시 URI: /student/12/101/25
        int grade = 0;
        int room = 0;
        int number = 0;
        // 배열의 길이와 위치 확인에 주의 (예제에서는 API 접두어가 포함되어 있습니다)
        if (pathParts.length > 5) {
            grade = Integer.parseInt(pathParts[3]);
            room = Integer.parseInt(pathParts[4]); // '101'
            number = Integer.parseInt(pathParts[5]); // '25'
        }

        //클라이언트 요청 Username, password 추출
        boolean isExist = studentRepository.existsByGradeAndRoomAndNumber(grade, room, number);
        if (!isExist) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "There's no corresponding student",
                    new IllegalArgumentException());
        }
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        UUID studentId = student.getId();
        System.out.println(studentId);
        System.out.println(student.getName());
        String password = loginRequestDto.password;
        System.out.println("password:" + password);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야함
        //TODO: role 값 설정(임시로 null 처리함)
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(studentId, password, null);
        System.out.println("======= Authentic =======");
        System.out.println(authToken);

        return studentAuthenticationProvider.authenticate(authToken);
    }
    //로그인 성공시 실행하는 메소드 (JWT 발급), spring security 에서 자동실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        System.out.println("======= 로그인 성공 =======");

        //유저 정보
        String username = authentication.getName();
        System.out.println(username);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        System.out.println("======= 토큰 생성 =======");
        String access = jwtUtil.createJwt("access", username, role, 9999600000L);

        AccountDto.LoginResponseDto tokens = AccountDto.LoginResponseDto.builder()
                                                                        .accessToken(access)
                                                                        .refreshToken(null)
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
}
