package com.atibo.backendspring.attendance.domain;

import com.atibo.backendspring.students.domain.Student;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;
    private LocalDateTime dateAttended;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public Attendance() {
    }

    public Attendance(LocalDateTime dateAttended, Student student) {
        this.dateAttended = dateAttended;
        this.student = student;
    }
}
