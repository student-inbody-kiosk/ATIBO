package com.atibo.backendspring.inbodys.application;

import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.inbodys.dto.InbodyDto;
import com.atibo.backendspring.inbodys.repository.InbodyRepository;
import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.repository.StudentRepository;

import org.springframework.stereotype.Service;

@Service
public class InbodyService {
    private final StudentRepository studentRepository;
    private final InbodyRepository inbodyRepository;

    public InbodyService(StudentRepository studentRepository, InbodyRepository inbodyRepository) {
        this.studentRepository = studentRepository;
        this.inbodyRepository = inbodyRepository;
    }

    public InbodyDto createInbody(int grade, int room, int number, InbodyDto.createInbodyRequest inbodyRequest) {
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        inbodyRequest.setStudent(student);
        Inbody data = inbodyRequest.convertToEntity();
        inbodyRepository.save(data);
        return new InbodyDto(data);
    }
}