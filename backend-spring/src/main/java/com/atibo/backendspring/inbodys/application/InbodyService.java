package com.atibo.backendspring.inbodys.application;

import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.inbodys.dto.InbodyDto;
import com.atibo.backendspring.inbodys.repository.InbodyRepository;
import com.atibo.backendspring.students.application.StudentService;
import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.dto.StudentWithInbodyDto;
import com.atibo.backendspring.students.repository.StudentRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InbodyService {
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final InbodyRepository inbodyRepository;

    public InbodyService(StudentService studentService, StudentRepository studentRepository, InbodyRepository inbodyRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.inbodyRepository = inbodyRepository;
    }

    public InbodyDto createInbody(int grade, int room, int number, InbodyDto.createInbodyRequest inbodyRequest) {
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        if (student == null) {
            throw new IllegalArgumentException("학생없음");
        }
        inbodyRequest.setStudent(student);
        Inbody data = inbodyRequest.convertToEntity();
        inbodyRepository.save(data);
        return new InbodyDto(data);
    }

    public List<InbodyDto> getInbodyList(Integer grade, Integer room, Integer number, LocalDate startDate, LocalDate endDate) {
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        List<Inbody> inbodyList = inbodyRepository.findByStudentAndTestDateBetween(student, startDate, endDate);
        return inbodyList.stream().map(InbodyDto::new).toList();
    }

    public InbodyDto getInbody(Integer inbodyId) {
        Inbody data = inbodyRepository.findInbodyById(inbodyId);
        return new InbodyDto(data);
    }

    public InbodyDto changeInbody(Integer inbodyId, InbodyDto.createInbodyRequest inbodyRequest) {
        Inbody data = inbodyRepository.findInbodyById(inbodyId);
        data.update(inbodyRequest.getTestDate(),
                inbodyRequest.getWeight(),
                inbodyRequest.getPercentBodyFat(),
                inbodyRequest.getSkeletalMuscleMass(),
                inbodyRequest.getHeight(),
                inbodyRequest.getAge(),
                inbodyRequest.getTotalBodyWater(),
                inbodyRequest.getProtein(),
                inbodyRequest.getMinerals(),
                inbodyRequest.getBodyFatMass(),
                inbodyRequest.getBodyMassIndex(),
                inbodyRequest.getScore());
        inbodyRepository.save(data);
        return new InbodyDto(data);
    }

    public void deleteInbody(Integer inbodyId) {
        inbodyRepository.deleteById(inbodyId);
    }

    public List<StudentWithInbodyDto> searchInbodyList(Integer grade, Integer room, Integer number, String name, LocalDate startDate, LocalDate endDate) {
        Specification<Student> criteria = studentService.buildCriteria(grade, room, number, name);
        List<Student> students = studentRepository.findAll(criteria);
        List<StudentWithInbodyDto> result = new ArrayList<>();
        for (Student student : students) {
            addData(result, student, startDate, endDate);
        }
        return result;
    }

    public void addData(List<StudentWithInbodyDto> result, Student student, LocalDate startDate, LocalDate endDate) {
        List<Inbody> inbodyList = inbodyRepository.findByStudentAndTestDateBetween(student, startDate, endDate);
        if (inbodyList != null) {
            result.add(new StudentWithInbodyDto(student, inbodyList));
        }
    }

    public List<InbodyDto> changeInbodyList(List<InbodyDto> request) {
        for (InbodyDto inbodyDto : request) {
            if (inbodyDto.getId() != 0) {
                updateInbody(inbodyDto.getId(), inbodyDto);
            }
        }
        return request;
    }

    public void updateInbody(int id, InbodyDto inbodyDto) {
        Inbody data = inbodyRepository.findInbodyById(id);
        data.update(inbodyDto.getTestDate(),
                inbodyDto.getWeight(),
                inbodyDto.getPercentBodyFat(),
                inbodyDto.getSkeletalMuscleMass(),
                inbodyDto.getHeight(),
                inbodyDto.getAge(),
                inbodyDto.getTotalBodyWater(),
                inbodyDto.getProtein(),
                inbodyDto.getMinerals(),
                inbodyDto.getBodyFatMass(),
                inbodyDto.getBodyMassIndex(),
                inbodyDto.getScore());
    }

    public void deleteInbodyList(InbodyDto.inbodyDeleteRequest request) {
        List<Integer> ids = request.getIds();
        List<Inbody> inbodyList = inbodyRepository.findByIdIn(ids);
        inbodyRepository.deleteAllInBatch(inbodyList);
    }
}
