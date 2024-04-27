package com.atibo.backendspring.students.controller;

import com.atibo.backendspring.students.application.StudentService;
import com.atibo.backendspring.students.dto.StudentDetailResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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

    @PostMapping("/api/students/{grade}/{room}/{number}/login/")
    public ResponseEntity<StudentDetailResponse> loginStudent(@PathVariable Integer grade,
                                                              @PathVariable Integer room,
                                                              @PathVariable Integer number
    ) {
        System.out.println("학생 로그인");
        StudentDetailResponse studentDetail = studentService.studentDetail(grade, room, number);
        return new ResponseEntity<>(studentDetail, HttpStatus.OK);
    }
}
