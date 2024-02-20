package com.atibo.backendspring.accounts.repository;

import com.atibo.backendspring.accounts.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
