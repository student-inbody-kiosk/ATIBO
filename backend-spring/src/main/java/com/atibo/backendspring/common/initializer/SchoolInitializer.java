package com.atibo.backendspring.common.initializer;

import com.atibo.backendspring.config.ConfigEnv;
import com.atibo.backendspring.school.application.SchoolService;
import com.atibo.backendspring.school.domain.School;
import com.atibo.backendspring.school.repository.SchoolRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SchoolInitializer implements ApplicationRunner {

    private final SchoolRepository schoolRepository;
    private final ConfigEnv configEnv;

    public SchoolInitializer(SchoolRepository schoolRepository, ConfigEnv configEnv) {
        this.schoolRepository = schoolRepository;
        this.configEnv = configEnv;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!schoolRepository.existsById(0)) {
            School school = new School();
            System.out.println(configEnv.getLocalDir());
            school.updateSchool("아티초등학교", "/src/main/resources/static/logo/default-logo-image.png");
            schoolRepository.save(school);
        }
    }
}
