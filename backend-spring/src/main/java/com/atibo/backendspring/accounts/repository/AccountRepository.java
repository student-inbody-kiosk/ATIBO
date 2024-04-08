package com.atibo.backendspring.accounts.repository;

import com.atibo.backendspring.accounts.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Account findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
