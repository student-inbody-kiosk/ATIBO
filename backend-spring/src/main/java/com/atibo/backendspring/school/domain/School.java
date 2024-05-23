package com.atibo.backendspring.school.domain;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.File;


@Entity
public class School {
    @Id
    private final int id = 1;
    private String name;
    private File logoImage;

    public School() {
    }

    public void updateSchool(String name, File logoImage) {
        this.name = name;
        this.logoImage = logoImage;
    }

    public String getName() {
        return name;
    }

    public File getLogoImage() {
        return logoImage;
    }
}
