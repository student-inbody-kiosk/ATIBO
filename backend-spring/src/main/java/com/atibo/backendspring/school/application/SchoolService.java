package com.atibo.backendspring.school.application;

import com.atibo.backendspring.school.domain.School;
import com.atibo.backendspring.school.dto.SchoolDto;
import com.atibo.backendspring.school.repository.SchoolRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public SchoolDto getSchool() {
        School school = schoolRepository.findSchoolById(1);
        return new SchoolDto(school);
    }

    public SchoolDto changeSchool(SchoolDto schoolDto) {
        School school = schoolRepository.findSchoolById(1);
        System.out.println(school.getName());
        school.updateSchool(schoolDto.getName(), schoolDto.getLogoImage());

        return new SchoolDto(school);
    }
}
