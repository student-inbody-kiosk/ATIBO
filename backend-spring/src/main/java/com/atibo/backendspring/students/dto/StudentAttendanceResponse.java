package com.atibo.backendspring.students.dto;

import com.atibo.backendspring.attendance.dto.AttendanceResponseDto;
import com.atibo.backendspring.students.domain.Student;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class StudentAttendanceResponse {
    private String name;
    private int grade;
    private int room;
    private int number;
    private Map<Integer, List<AttendanceResponseDto>> attendanceSet;

    public StudentAttendanceResponse() {
    }

    public StudentAttendanceResponse(Student student, Map<Integer, List<AttendanceResponseDto>> attendanceSet) {
        this.name = student.getName();
        this.grade = student.getGrade();
        this.room = student.getRoom();
        this.number = student.getNumber();
        this.attendanceSet = attendanceSet;
    }
}
