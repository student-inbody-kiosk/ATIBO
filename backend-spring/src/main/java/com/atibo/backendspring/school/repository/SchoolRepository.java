package com.atibo.backendspring.school.repository;

import com.atibo.backendspring.school.domain.School;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SchoolRepository extends JpaRepository<School, Integer> {
    School findSchoolById(Integer id);
}
