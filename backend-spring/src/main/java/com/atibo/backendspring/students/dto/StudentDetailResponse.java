package com.atibo.backendspring.students.dto;

import com.atibo.backendspring.students.domain.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class StudentDetailResponse {
    private UUID id;
    private String name;
    private int grade;
    private int room;
    private int number;
    private int sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date birthDate;

    public StudentDetailResponse() {
    }

    public StudentDetailResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.grade = student.getGrade();
        this.room = student.getRoom();
        this.number = student.getNumber();
        this.sex = student.getSex();
        this.birthDate = student.getBirth_date();
    }
}
