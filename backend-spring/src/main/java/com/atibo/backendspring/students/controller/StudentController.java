package com.atibo.backendspring.students.controller;

import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.inbodys.application.InbodyService;
import com.atibo.backendspring.inbodys.dto.InbodyDto;
import com.atibo.backendspring.students.application.StudentService;
import com.atibo.backendspring.students.dto.StudentDetailResponse;
import com.atibo.backendspring.students.dto.StudentDto;
import com.atibo.backendspring.students.dto.StudentWithInbodyDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/api/students/inbody/{grade}/{room}/{number}/")
    public ResponseEntity<List<InbodyDto>> getStudentInbodyList(@PathVariable Integer grade,
                                                                @PathVariable Integer room,
                                                                @PathVariable Integer number,
                                                                @RequestParam("startDate") LocalDate startDate,
                                                                @RequestParam("endDate") LocalDate endDate) {
        System.out.println("학생 인바디 조회");
        List<InbodyDto> inbodyList = inbodyService.getInbodyList(grade, room, number, startDate, endDate);
        return new ResponseEntity<>(inbodyList, HttpStatus.OK);
    }

    @GetMapping("/api/students/inbody/{inbodyId}/")
    public ResponseEntity<InbodyDto> getInbody(@PathVariable Integer inbodyId) {
        System.out.println("인바디 디테일 조회");
        InbodyDto inbody = inbodyService.getInbody(inbodyId);
        return new ResponseEntity<>(inbody, HttpStatus.OK);
    }

    @GetMapping("/api/students/inbody/{startDate}/{endDate}/")
    public ResponseEntity<List<StudentWithInbodyDto>> getInbodyList(@PathVariable LocalDate startDate,
                                                                    @PathVariable LocalDate endDate,
                                                                    @RequestParam(required = false) Integer grade,
                                                                    @RequestParam(required = false) Integer room,
                                                                    @RequestParam(required = false) Integer number,
                                                                    @RequestParam(required = false) String name) {
        System.out.println("인바디 조회");
        List<StudentWithInbodyDto> inbodyList = inbodyService.searchInbodyList(grade, room, number, name, startDate, endDate);
        return new ResponseEntity<>(inbodyList, HttpStatus.OK);
    }

    @PutMapping("/api/students/inbody/{inbodyId}/")
    public ResponseEntity<InbodyDto> changeInbody(@PathVariable Integer inbodyId,
                                                  @RequestBody InbodyDto.createInbodyRequest inbodyRequest) {
        System.out.println("인바디 수정");
        InbodyDto inbodyDto = inbodyService.changeInbody(inbodyId, inbodyRequest);
        return new ResponseEntity<>(inbodyDto, HttpStatus.OK);
    }

    @DeleteMapping("/api/students/inbody/{inbodyId}/")
    public ResponseEntity<Response> deleteInbody(@PathVariable Integer inbodyId) {
        System.out.println("인바디 삭제");
        inbodyService.deleteInbody(inbodyId);
        Response response = new Response("The Inbody is successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/students/inbody/list/")
    public ResponseEntity<List<InbodyDto>> changeInbodyLis(@RequestBody List<InbodyDto> request) {
        // TODO: id 가 null 일 경우 새로 생성해야하는데 학생 구별이 되지 않는 문제가 있음(프론트에서 학생을 넘겨주면 좋을듯) null 이 아닌 인바디를 통해 가져오려고 해도 전부다 null인 요청이 들어오면 불가능함
        System.out.println("인바디 목록 수정");
        List<InbodyDto> inbodyDtoList = inbodyService.changeInbodyList(request);
        return new ResponseEntity<>(inbodyDtoList, HttpStatus.OK);
    }
}
