package com.atibo.backendspring.accounts.repository;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByAccount(Account account);

}