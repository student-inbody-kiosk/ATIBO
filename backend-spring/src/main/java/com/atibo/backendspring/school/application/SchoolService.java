package com.atibo.backendspring.school.application;

import com.atibo.backendspring.school.domain.School;
import com.atibo.backendspring.school.dto.SchoolDto;
import com.atibo.backendspring.school.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@Service
public class SchoolService {
    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String LOCAL_DIR = "http://localhost:8080/";
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public SchoolDto getSchool() {
        School school = schoolRepository.findSchoolById(1);
        return new SchoolDto(school);
    }

    public SchoolDto changeSchool(Optional<MultipartFile> logoImage, String name) {
        School school = schoolRepository.findSchoolById(1);
        System.out.println(school.getName());
//        if (logoImage.isEmpty()) {
//            System.out.println("이름만변경");
//            school.changeName(name);
//            schoolRepository.save(school);
//            return new SchoolDto(school);
//        }
//TODO: 디렉토리 초기화 할꺼면 사용(사진 1장만 사용시)...

//        try {
//            Files.walkFileTree(Path.of(UPLOAD_DIR), new SimpleFileVisitor<Path>() {
//                @Override
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                    Files.delete(file);
//                    System.out.println("파일이 삭제되었습니다: " + file.toString());
//                    return FileVisitResult.CONTINUE;
//                }
//
//            });
//        } catch (IOException e) {
//            System.err.println("디렉토리를 비우는 도중 오류가 발생했습니다: " + e.getMessage());
//        }

        try {
            // 디렉토리가 존재하지 않으면 생성
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일을 저장할 경로 설정
            String fileName = logoImage.get().getOriginalFilename();
            assert fileName != null;
            Path filePath = uploadPath.resolve(fileName);

            // 파일 저장
            if (!Files.exists(filePath)) {
                Files.copy(logoImage.get().getInputStream(), filePath);
            }

            school.updateSchool(name, LOCAL_DIR + filePath.toString());
            schoolRepository.save(school);

            return new SchoolDto(school);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}