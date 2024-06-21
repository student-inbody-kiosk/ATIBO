package com.atibo.backendspring.school.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class School {
    @Id
    private final int id = 0;
    private String name;
    private String logoImagePath;

    public School() {
    }

    public void updateSchool(String name, String logoImage) {
        this.name = name;
        this.logoImagePath = logoImage;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLogoImagePath() {
        return logoImagePath;
    }
}
