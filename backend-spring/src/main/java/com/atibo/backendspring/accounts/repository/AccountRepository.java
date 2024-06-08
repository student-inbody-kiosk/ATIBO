package com.atibo.backendspring.accounts.repository;

import com.atibo.backendspring.accounts.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

    boolean existsById(UUID uuid);

    boolean existsByEmail(String email);

    Account findByUsername(String username);

    Account findById(UUID uuid);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    void deleteById(UUID uuid);
}
