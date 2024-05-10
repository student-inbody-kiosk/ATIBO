package com.atibo.backendspring.inbodys.repository;

import com.atibo.backendspring.inbodys.domain.Inbody;
import com.atibo.backendspring.students.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface InbodyRepository extends JpaRepository<Inbody, Long>, JpaSpecificationExecutor<Inbody> {
}
