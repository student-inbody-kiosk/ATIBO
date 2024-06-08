package com.atibo.backendspring.attendance.controller;

import com.atibo.backendspring.attendance.application.AttendanceService;
import com.atibo.backendspring.attendance.dto.AttendanceDto;
import com.atibo.backendspring.students.dto.StudentAttendanceResponse;
import com.atibo.backendspring.students.dto.StudentWithInbodyDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/api/students/attendance/{grade}/{room}/{number}/")
    public ResponseEntity<AttendanceDto> attendanceCheck(@PathVariable Integer grade,
                                                         @PathVariable Integer room,
                                                         @PathVariable Integer number) {
        System.out.println("출석체크");
        AttendanceDto response = attendanceService.attendanceCheck(grade, room, number);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/students/attendance/{month}/")
    public ResponseEntity<List<StudentAttendanceResponse>> attendanceList(@PathVariable String month,
                                            @RequestParam(required = false) Integer grade,
                                            @RequestParam(required = false) Integer room,
                                            @RequestParam(required = false) Integer number,
                                            @RequestParam(required = false) String name) {
        System.out.println("출결 조회");
        System.out.println(month);
        List<StudentAttendanceResponse> response = attendanceService.getAttendanceList(month, grade, room, number, name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
