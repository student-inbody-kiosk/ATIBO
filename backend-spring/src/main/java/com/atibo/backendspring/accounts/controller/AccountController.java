package com.atibo.backendspring.accounts.controller;

import com.atibo.backendspring.accounts.application.AccountService;
import com.atibo.backendspring.accounts.application.EmailService;
import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.accounts.repository.RefreshRepository;
import com.atibo.backendspring.common.jwt.JWTUtil;
import com.atibo.backendspring.students.application.StudentService;
import com.atibo.backendspring.students.dto.StudentDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.ExpiredJwtException;

import java.util.List;


@Controller
@ResponseBody
public class AccountController {
    private final AccountRepository accountRepository;
    private final RefreshRepository refreshRepository;
    private final AccountService accountService;
    private final EmailService emailService;
    private final StudentService studentService;
    private final JWTUtil jwtUtil;

    public AccountController(AccountRepository accountRepository, AccountService accountService, RefreshRepository refreshRepository, EmailService emailService, StudentService studentService, JWTUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.refreshRepository = refreshRepository;
        this.emailService = emailService;
        this.studentService = studentService;
        this.jwtUtil = jwtUtil;
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
        System.out.println("token_refresh");
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
        System.out.println("비밀번호 초기화");
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

    @PutMapping("/api/accounts/password/change/")
    public ResponseEntity<?> changePassword(@RequestBody AccountDto.changePasswordDto request) {
        accountService.changePassword(request);
        Response response = new Response("The password is changed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/students/")
    public ResponseEntity<?> saveStudents(@RequestBody List<StudentDto> students) {
        studentService.saveStudents(students);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/api/students/")
    public ResponseEntity<List<StudentDto>> findStudents(@RequestParam(required = false) Integer grade,
                                                     @RequestParam(required = false) Integer room,
                                                     @RequestParam(required = false) Integer number,
                                                     @RequestParam(required = false) String name) {
        System.out.println("학생 조회");
        List<StudentDto> studentDtos = studentService.findStudents(grade, room, number, name);
        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
    }

    @PutMapping("/api/students/")
    public ResponseEntity<List<StudentDto>> updateStudents(@RequestBody List<StudentDto> students) {
        System.out.println("학생 목록 수정");
        List<StudentDto> studentDtos = studentService.updateStudents(students);
        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
    }

    @PatchMapping("/api/students/")
    public ResponseEntity<?> deleteStudents(@RequestBody StudentDto.deleteStudentDto request) {
        System.out.println("학생 정보 삭제");
        studentService.deleteStudents(request);
        Response response = new Response("Deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
