package com.atibo.backendspring.inbodys.repository;

import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.students.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface InbodyRepository extends JpaRepository<Inbody, Long> {
    @Query("SELECT i FROM Inbody i WHERE i.student = :student AND i.test_date BETWEEN :startDate AND :endDate")
    List<Inbody> findByStudentAndTestDateBetween(Student student, LocalDate startDate, LocalDate endDate);
}
