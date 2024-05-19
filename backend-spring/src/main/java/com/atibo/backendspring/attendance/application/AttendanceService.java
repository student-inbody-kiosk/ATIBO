package com.atibo.backendspring.attendance.application;

import com.atibo.backendspring.attendance.domain.Attendance;
import com.atibo.backendspring.attendance.dto.AttendanceDto;
import com.atibo.backendspring.attendance.dto.AttendanceResponseDto;
import com.atibo.backendspring.attendance.repository.AttendanceRepository;
import com.atibo.backendspring.students.application.StudentService;
import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.dto.StudentAttendanceResponse;
import com.atibo.backendspring.students.repository.StudentRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final EntityManager entityManager;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, EntityManager entityManager) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.entityManager = entityManager;
    }

    public AttendanceDto attendanceCheck(Integer grade, Integer room, Integer number) {
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        boolean isAttendance = isAttendanced(student);
        if (!isAttendance) {
            throw new RuntimeException("30분 이내 출석이 되어있습니다.");
        }
        Attendance attendance = new Attendance(LocalDateTime.now().withNano(0), student);
        attendanceRepository.save(attendance);
        return new AttendanceDto(attendance);
    }

    private boolean isAttendanced(Student student) {
        Attendance latestAttendance = attendanceRepository.findTopByStudentOrderByDateAttendedDesc(student);
        return latestAttendance == null || latestAttendance.getDateAttended()
                                                           .plusMinutes(30)
                                                           .isBefore(LocalDateTime.now());
    }

    public List<Student> findStudentsAttendedMonth(LocalDate date, Integer grade, Integer room, Integer number, String name) {
        // Criteria 빌더 생성
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> studentRoot = query.from(Student.class);

        // Join을 통해 출석 기록에 접근
        Join<Student, Attendance> attendanceJoin = studentRoot.join("attendances", JoinType.INNER);

        // 조건을 담을 리스트 생성
        List<Predicate> predicates = new ArrayList<>();

        // grade, room, number, name 조건이 존재하는 경우에만 추가
        if (grade != 0) {
            predicates.add(cb.equal(studentRoot.get("grade"), grade));
        }
        if (room != 0) {
            predicates.add(cb.equal(studentRoot.get("room"), room));
        }
        if (number != 0) {
            predicates.add(cb.equal(studentRoot.get("number"), number));
        }
        if (name != null) {
            predicates.add(cb.like(studentRoot.get("name"), "%" + name + "%"));
        }

        Expression<Integer> year = cb.function("year", Integer.class, attendanceJoin.get("dateAttended"));
        Expression<Integer> month = cb.function("month", Integer.class, attendanceJoin.get("dateAttended"));
        predicates.add(cb.equal(cb.function("year", Integer.class, attendanceJoin.get("dateAttended")), date.getYear()));
        predicates.add(cb.equal(cb.function("month", Integer.class, attendanceJoin.get("dateAttended")), date.getMonthValue()));

        // 모든 필터링 조건을 AND로 결합
        query.where(predicates.toArray(new Predicate[0]));

        // 결과를 조회하여 반환
        return entityManager.createQuery(query).getResultList();
    }

    public List<StudentAttendanceResponse> getAttendanceList(String yearMonth, Integer grade, Integer room, Integer number, String name) {
        //TODO: 메서드 분리하기 (2중 depth, 10줄 미만)
        String[] parts = yearMonth.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        LocalDate date = LocalDate.of(year, month, 1);

        List<Student> students = findStudentsAttendedMonth(date, grade, room, number, name);
        List<StudentAttendanceResponse> responseList = new ArrayList<>();

        for (Student student : students) {
            List<Attendance> attendances = student.getAttendances();
            Map<Integer, List<AttendanceResponseDto>> attendanceMap = new HashMap<>();

            for (Attendance attendance : attendances) {
                int day = attendance.getDateAttended().getDayOfMonth();
                List<AttendanceResponseDto> attendanceResponseDtoList = attendanceMap.getOrDefault(day, new ArrayList<>());
                attendanceResponseDtoList.add(new AttendanceResponseDto(attendance));
                attendanceMap.put(day, attendanceResponseDtoList);
            }
            StudentAttendanceResponse response = new StudentAttendanceResponse(student, attendanceMap);
            responseList.add(response);
            }
        return responseList;
    }
}
