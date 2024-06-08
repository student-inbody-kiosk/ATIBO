package com.atibo.backendspring.students.security;

import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class StudentDetailsService implements StudentDetailService{
    private final StudentRepository studentRepository;
    public StudentDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDetails loadUserById(UUID studentId) {

        Student studentData = studentRepository.findById(studentId);
        if (studentData != null) {
            return new StudentDetails(studentData);
        }
        return null;
    }
}
