package com.atibo.backendspring.students.application;

import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.jwt.JWTUtil;
import com.atibo.backendspring.students.domain.Student;
import com.atibo.backendspring.students.dto.StudentDetailResponse;
import com.atibo.backendspring.students.dto.StudentDto;
import com.atibo.backendspring.students.repository.StudentRepository;
import com.atibo.backendspring.students.security.StudentAuthenticationProvider;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.criteria.Predicate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentAuthenticationProvider studentAuthenticationProvider;
    private final JWTUtil jwtUtil;

    public StudentService(StudentRepository studentRepository, StudentAuthenticationProvider studentAuthenticationProvider, JWTUtil jwtUtil) {
        this.studentRepository = studentRepository;

        this.studentAuthenticationProvider = studentAuthenticationProvider;
        this.jwtUtil = jwtUtil;
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
        boolean isExist = studentRepository.existsByGradeAndRoomAndNumber(grade, room, number);
        if (!isExist) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "There's no corresponding student",
                    new IllegalArgumentException());
        }
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        return new StudentDetailResponse(student);
    }

    public StudentDto.StudentCheckResponse studentCheck(int grade, int room, int number) {
        boolean isExist = studentRepository.existsByGradeAndRoomAndNumber(grade, room, number);
        if (!isExist) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "There's no corresponding student",
                    new IllegalArgumentException());
        }
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        return new StudentDto.StudentCheckResponse(student);
    }

    public AccountDto.LoginResponseDto login(int grade, int room, int number, String password) {
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);
        Authentication auth = authentication(student, password);
        return createJWT(auth);
    }

    private Authentication authentication(Student student, String password) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(student.getId(), password);
        return studentAuthenticationProvider.authenticate(authToken);
    }

    private AccountDto.LoginResponseDto createJWT(Authentication authentication) {
        String id = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        System.out.println("======= 토큰 생성 =======");
        String access = jwtUtil.createJwt("access", id, role, 9999600000L);
        return AccountDto.LoginResponseDto.builder()
                                          .accessToken(access)
                                          .refreshToken(null)
                                          .build();
    }

    public void changePassword(int grade, int room, int number, AccountDto.changePasswordDto passwords) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByGradeAndRoomAndNumber(grade, room, number);

        if (student.getPassword() == passwords.getOldPassword()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 틀렸습니다.",
                    new IllegalArgumentException()
            );
        }
        //TODO: 학생 패스워드 검증
//        validPassword(passwords.getNewPassword());
        if (!Objects.equals(passwords.getNewPassword(), passwords.getConfirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 일치하지 않습니다.",
                    new IllegalArgumentException()
            );
        }
        student.changePassword(passwords.getNewPassword());
    }
}
