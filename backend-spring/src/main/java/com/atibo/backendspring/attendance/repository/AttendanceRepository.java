package com.atibo.backendspring.attendance.repository;
import com.atibo.backendspring.attendance.domain.Attendance;
import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.students.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Attendance findTopByStudentOrderByDateAttendedDesc(Student student);

    @Query("SELECT i FROM Attendance i WHERE i.student = :student AND i.dateAttended BETWEEN :startDate AND :endDate")
    List<Attendance> findByStudentAndAttendedDateBetween(Student student, LocalDateTime startDate, LocalDateTime endDate);
}
