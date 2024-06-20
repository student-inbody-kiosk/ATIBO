package com.atibo.backendspring.config;

import com.atibo.backendspring.school.domain.School;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class SchoolInitializer implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        School school = new School();
        // TODO: 이미지 경로 설정
    }
}
