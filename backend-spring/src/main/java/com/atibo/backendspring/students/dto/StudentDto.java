package com.atibo.backendspring.students.dto;

import com.atibo.backendspring.students.domain.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class StudentDto {

    private UUID id;
    private String name;
    private int grade;
    private int room;
    private int number;
    private int sex;
    private String password = "0000";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date birthDate;

    public StudentDto() {
    }

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.grade = student.getGrade();
        this.room = student.getRoom();
        this.number = student.getNumber();
        this.sex = student.getSex();
        this.password = student.getPassword();
        this.birthDate = student.getBirth_date();
    }

    public Student convertToEntity() {
        return new Student(
                this.name,
                this.grade,
                this.room,
                this.number,
                this.sex,
                this.password,
                this.birthDate);
    }

    public static class deleteStudentDto {
        private List<UUID> ids;

        public deleteStudentDto(List<UUID> ids) {
            this.ids = ids;
        }

        public deleteStudentDto() {
        }

        public List<UUID> getIds() {
            return this.ids;
        }
    }

    public static class StudentLoginRequest {
        public String password;
    }
}

