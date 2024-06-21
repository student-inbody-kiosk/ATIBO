package com.atibo.backendspring.config;

import com.atibo.backendspring.school.domain.School;
import com.atibo.backendspring.school.repository.SchoolRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SchoolInitializer implements ApplicationRunner {

    private final SchoolRepository schoolRepository;

    public SchoolInitializer(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!schoolRepository.existsById(0)) {
            School school = new School();
            school.updateSchool("아티초등학교", "http://localhost:8080/src/main/resources/static/logo/default-logo-image.png");
            schoolRepository.save(school);
        }
    }
}
