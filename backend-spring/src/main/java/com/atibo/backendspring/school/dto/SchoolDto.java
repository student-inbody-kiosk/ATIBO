package com.atibo.backendspring.school.dto;

import com.atibo.backendspring.school.domain.School;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

import java.io.File;

@Getter
public class SchoolDto {

    private String name;
    private String logoImage;

    public SchoolDto() {
    }

    public SchoolDto(School school) {
        this.name = school.getName();
        this.logoImage = school.getLogoImagePath();
    }

    public SchoolDto(String name, String logoImage) {
        this.name = name;
        this.logoImage = logoImage;
    }
}
