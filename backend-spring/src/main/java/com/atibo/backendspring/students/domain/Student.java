package com.atibo.backendspring.students.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
public class Student {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private int grade;
    @NotNull
    private int room;
    @NotNull
    private int number;
    @NotNull
    private int sex;
    @NotNull
    private String password;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birth_date;

    public Student(String name, int grade, int room, int number, int sex, String password, Date birthDate) {
        this.name = name;
        this.grade = grade;
        this.room = room;
        this.number = number;
        this.sex = sex;
        this.password = password;
        this.birth_date = birthDate;
    }

    public Student() {

    }
}
