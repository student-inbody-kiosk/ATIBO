package com.atibo.backendspring.inbodys.domain;

import com.atibo.backendspring.students.domain.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Inbody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate test_date;
    private int weight;
    private int percentBodyFat;
    private int skeletalMuscleMass;
    private int height;
    private int age;
    private int totalBodyWater;
    private int protein;
    private int minerals;
    private int bodyFatMass;
    private int bodyMassIndex;
    private int score;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public Inbody() {
    }

    public Inbody(LocalDate testDate,
                  int weight,
                  int percentBodyFat,
                  int skeletalMuscleMass,
                  int height,
                  int age,
                  int totalBodyWater,
                  int protein,
                  int minerals,
                  int bodyFatMass,
                  int bodyMassIndex,
                  int score,
                  Student student) {
        this.test_date = testDate;
        this.weight = weight;
        this.percentBodyFat = percentBodyFat;
        this.skeletalMuscleMass = skeletalMuscleMass;
        this.height = height;
        this.age = age;
        this.totalBodyWater = totalBodyWater;
        this.protein = protein;
        this.minerals = minerals;
        this.bodyFatMass = bodyFatMass;
        this.bodyMassIndex = bodyMassIndex;
        this.score = score;
        this.student = student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
