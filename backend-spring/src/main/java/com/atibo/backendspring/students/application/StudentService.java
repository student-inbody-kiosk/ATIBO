package com.atibo.backendspring.students.application;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.dto.StudentDto;
import com.atibo.backendspring.students.repository.StudentRepository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void saveStudents(List<StudentDto> studentsDTOs) {
        List<Student> students = studentsDTOs.stream()
                                             .map(StudentDto::convertToEntity)
                                             .collect(Collectors.toList());
        studentRepository.saveAll(students);
    }
    @Transactional(readOnly = true)
    public List<StudentDto> findStudents(Integer grade, Integer room, Integer number, String name) {
        Specification<Student> criteria = buildCriteria(grade, room, number, name);
        List<Student> students  = studentRepository.findAll(criteria);
        List<StudentDto> studentDtos = students.stream()
                                               .map(StudentDto::new)  // Student 객체를 StudentDto로 변환
                                               .toList();
        return studentDtos;
    }

    public static Specification<Student> buildCriteria(Integer grade, Integer room, Integer number, String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (grade != 0) {
                predicates.add(criteriaBuilder.equal(root.get("grade"), grade));
            }
            if (room != 0) {
                predicates.add(criteriaBuilder.equal(root.get("room"), room));
            }
            if (number != 0) {
                predicates.add(criteriaBuilder.equal(root.get("number"), number));
            }
            if (name != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
