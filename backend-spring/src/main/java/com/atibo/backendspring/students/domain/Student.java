package com.atibo.backendspring.students.domain;

import com.atibo.backendspring.attendance.domain.Attendance;
import com.atibo.backendspring.inbodys.domain.Inbody;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<Inbody> inbodyList;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Attendance> attendances;

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

    public void update(String name, int grade, int room, int number, int sex, String password, Date birth_date) {
        this.name = name;
        this.grade = grade;
        this.room = room;
        this.number = number;
        this.sex = sex;
        this.password = password;
        this.birth_date = birth_date;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public List<Inbody> getInbodyList() {
        return this.inbodyList;
    }
}
