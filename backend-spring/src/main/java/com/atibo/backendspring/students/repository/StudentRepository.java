package com.atibo.backendspring.students.repository;

import java.util.List;
import java.util.UUID;

import com.atibo.backendspring.students.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Student findById(UUID uuid);

    Student findByGradeAndRoomAndNumber(int grade, int room, int number);
    List<Student> findAll();
    List<Student> findByIdIn(List<UUID> uuids);
    void deleteByIdIn(List<UUID> uuids);

}
