package com.atibo.backendspring.students.application;

import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.dto.StudentDetailResponse;
import com.atibo.backendspring.students.dto.StudentDto;
import com.atibo.backendspring.students.repository.StudentRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public List<StudentDto> updateStudents(List<StudentDto> studentDtos) {
        List<UUID> uuids = studentDtos.stream().map(StudentDto::getId).toList();
        studentDtos.forEach(this::updateStudent);
        List<Student> students = studentRepository.findByIdIn(uuids);
        List<StudentDto> studentDTOs = students.stream().map(StudentDto::new)  // Student 객체를 StudentDto로 변환
                                               .toList();
        return studentDTOs;
    }

    private void updateStudent(StudentDto studentDto) {
        Student student = studentRepository.findById(studentDto.getId());
        student.update(studentDto.getName(), studentDto.getGrade(), studentDto.getRoom(), studentDto.getNumber(), studentDto.getSex(), studentDto.getPassword(), studentDto.getBirthDate());
        studentRepository.save(student);
    }

    public void deleteStudents(StudentDto.deleteStudentDto request) {
        List<UUID> uuids = request.getIds();
        studentRepository.deleteByIdIn(uuids);
        System.out.println("학생학제완료");
    }

    public StudentDetailResponse studentDetail(int grade, int room, int number) {
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        return new StudentDetailResponse(student);
    }
}
