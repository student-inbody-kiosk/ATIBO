package com.atibo.backendspring.school.dto;

import com.atibo.backendspring.school.domain.School;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

import java.io.File;

@Getter
public class SchoolDto {

    private String name;
    private File logoImage;

    public SchoolDto() {
    }

    public SchoolDto(School school) {
        this.name = school.getName();
        this.logoImage = school.getLogoImage();
    }
}
