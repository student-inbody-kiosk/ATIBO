package com.atibo.backendspring.inbodys.dto;

import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.students.domain.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class InbodyDto {
    private int id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate testDate;
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

    public InbodyDto() {
    }

    public InbodyDto(Inbody inbody) {
        this.id = inbody.getId();
        this.testDate = inbody.getTest_date();
        this.weight = inbody.getWeight();
        this.percentBodyFat = inbody.getPercentBodyFat();
        this.skeletalMuscleMass = inbody.getSkeletalMuscleMass();
        this.height = inbody.getHeight();
        this.age = inbody.getAge();
        this.totalBodyWater = inbody.getTotalBodyWater();
        this.protein = inbody.getProtein();
        this.minerals = inbody.getMinerals();
        this.bodyFatMass = inbody.getBodyFatMass();
        this.bodyMassIndex = inbody.getBodyMassIndex();
        this.score = inbody.getScore();
    }

    @Getter
    public static class createInbodyRequest {
        @Temporal(TemporalType.DATE)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate testDate;
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
        private Student student;

        public createInbodyRequest() {
        }

        public Inbody convertToEntity() {
            return new Inbody(
                    this.testDate,
                    this.weight,
                    this.percentBodyFat,
                    this.skeletalMuscleMass,
                    this.height,
                    this.age,
                    this.totalBodyWater,
                    this.protein,
                    this.minerals,
                    this.bodyFatMass,
                    this.bodyMassIndex,
                    this.score,
                    this.student
            );
        }

        public void setStudent(Student student) {
            this.student = student;
        }
    }
}
