package com.atibo.backendspring.students.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface StudentDetailService {
    UserDetails loadUserById(UUID studentId);
}
