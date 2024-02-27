package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.CustomUserDetails;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CusetomUserDetailsService implements UserDetailsService {

    // TODO: 생성자 주입으로 바꾸기
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account accountData = accountRepository.findByUsername(username);

        if (accountData != null) {

            return new CustomUserDetails(accountData);
        }

        return null;
    }
}
