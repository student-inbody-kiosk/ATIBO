package com.atibo.backendspring.attendance.dto;

import com.atibo.backendspring.attendance.domain.Attendance;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class AttendanceResponseDto {
    private int id;
    private LocalTime time;

    public AttendanceResponseDto() {
    }

    public AttendanceResponseDto(Attendance attendance) {
        this.id = attendance.getId();
        this.time = attendance.getDateAttended().toLocalTime();
    }
}
