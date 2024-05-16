package com.atibo.backendspring.students.controller;

import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.inbodys.application.InbodyService;
import com.atibo.backendspring.inbodys.dto.InbodyDto;
import com.atibo.backendspring.students.application.StudentService;
import com.atibo.backendspring.students.dto.StudentDetailResponse;
import com.atibo.backendspring.students.dto.StudentDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final InbodyService inbodyService;

    public StudentController(StudentService studentService, InbodyService inbodyService) {
        this.studentService = studentService;
        this.inbodyService = inbodyService;
    }

    @GetMapping("/api/students/{grade}/{room}/{number}/")
    public ResponseEntity<StudentDetailResponse> findStudents(@PathVariable Integer grade,
                                                              @PathVariable Integer room,
                                                              @PathVariable Integer number
    ) {
        System.out.println("학생 조회");
        StudentDetailResponse studentDetail = studentService.studentDetail(grade, room, number);
        return new ResponseEntity<>(studentDetail, HttpStatus.OK);
    }

    @GetMapping("/api/students/{grade}/{room}/{number}/check/")
    public ResponseEntity<?> checkStudents(@PathVariable Integer grade,
                                           @PathVariable Integer room,
                                           @PathVariable Integer number
    ) {
        //TODO: unique 조건 에러처리 해야함
        StudentDto.StudentCheckResponse response = studentService.studentCheck(grade, room, number);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/students/{grade}/{room}/{number}/login/")
    public ResponseEntity<StudentDetailResponse> loginStudent(@PathVariable Integer grade,
                                                              @PathVariable Integer room,
                                                              @PathVariable Integer number,
                                                              @RequestBody StudentDto.StudentLoginRequest request
    ) {
        System.out.println("학생 로그인");
        studentService.login(grade, room, number, request.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/students/{grade}/{room}/{number}/password/change/")
    public ResponseEntity<?> changeStudentPassword(@PathVariable Integer grade,
                                                   @PathVariable Integer room,
                                                   @PathVariable Integer number,
                                                   @RequestBody AccountDto.changePasswordDto passwords
    ) {
        System.out.println("학생 비밀번호 변경");
        studentService.changePassword(grade, room, number, passwords);
        Response response = new Response("The password is changed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/students/inbody/{grade}/{room}/{number}/")
    public ResponseEntity<InbodyDto> createInbody(@PathVariable Integer grade,
                                                  @PathVariable Integer room,
                                                  @PathVariable Integer number,
                                                  @RequestBody InbodyDto.createInbodyRequest inbodyRequest) {
        System.out.println("인바디 디테일 생성");
        InbodyDto inbody = inbodyService.createInbody(grade, room, number, inbodyRequest);
        return new ResponseEntity<>(inbody, HttpStatus.OK);
    }
}
