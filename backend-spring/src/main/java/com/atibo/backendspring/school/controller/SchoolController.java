package com.atibo.backendspring.school.controller;

import com.atibo.backendspring.school.application.SchoolService;
import com.atibo.backendspring.school.dto.SchoolDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PutMapping("/api/school/")
    public ResponseEntity<SchoolDto> changeSchool(@RequestParam("logoImage") MultipartFile logoImage,
                                                  @RequestParam("name") String name) {

        System.out.println("학교정보 수정");
        System.out.println(logoImage);
        System.out.println(name);
//        SchoolDto response = schoolService.changeSchool(schoolDto);
        SchoolDto response = new SchoolDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/school/")
    public ResponseEntity<SchoolDto> getSchool() {
        System.out.println("학교정보 조회");
        SchoolDto response = schoolService.getSchool();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
