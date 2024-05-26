package com.atibo.backendspring.school.controller;

import com.atibo.backendspring.school.application.SchoolService;
import com.atibo.backendspring.school.dto.SchoolDto;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class SchoolController {
    private final SchoolService schoolService;
    public static final String FILE_DIRECTORY = "src/main/resources/static/";

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PutMapping("/api/school/")
    public ResponseEntity<SchoolDto> changeSchool(@RequestParam(required = false, value = "logoImage")Optional<MultipartFile> logoImageOptional,
                                                  @RequestParam("name") String name) {

        System.out.println("학교정보 수정");
        System.out.println(name);
        SchoolDto response = schoolService.changeSchool(logoImageOptional, name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/school/")
    public ResponseEntity<SchoolDto> getSchool() {
        System.out.println("학교정보 조회");
        SchoolDto response = schoolService.getSchool();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/src/main/resources/static/logo/{filePath:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filePath) {
        System.out.println("이미지 전송");
        Path fileLocation = Paths.get(FILE_DIRECTORY+"/logo").resolve(filePath);
        Resource resource;
        try {
            resource = new UrlResource(fileLocation.toUri());
        } catch (MalformedURLException e) {
            // Handle malformed URL exception
            return ResponseEntity.notFound().build();
        }
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                                 .body(resource);
        } else {
            // Handle file not found or unreadable
            return ResponseEntity.notFound().build();
        }
    }
}
