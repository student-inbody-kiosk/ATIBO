package com.atibo.backendspring.attendance.dto;

import com.atibo.backendspring.attendance.domain.Attendance;
import com.atibo.backendspring.students.domain.Student;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AttendanceDto {
    private int id;
    private LocalDateTime dateAttended;

    public AttendanceDto() {
    }

    public AttendanceDto(Attendance attendance) {
        this.id = attendance.getId();
        this.dateAttended = attendance.getDateAttended();
    }
}
