package com.atibo.backendspring.students.dto;

import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.inbodys.dto.InbodyDto;
import com.atibo.backendspring.students.domain.Student;

import lombok.Getter;

import java.util.List;

@Getter
public class StudentWithInbodyDto {
    private String name;
    private int grade;
    private int room;
    private int number;
    private int sex;
    private List<InbodyDto> inbodySet;

    public StudentWithInbodyDto() {
    }

    public StudentWithInbodyDto(Student student, List<Inbody> inbodyList) {
        this.name = student.getName();
        this.grade = student.getGrade();
        this.room = student.getRoom();
        this.number = student.getNumber();
        this.sex = student.getSex();
        // Inbody 목록을 InbodyDto로 변환하여 설정합니다.
        this.inbodySet = InbodyDto.convertToDtoList(inbodyList);
    }
}
