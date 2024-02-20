package com.atibo.backendspring.accounts.domain;

import com.atibo.backendspring.accounts.dto.AccountDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username")
    @NotNull
    private String username;

    private String password;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String comment;

    @Builder
    public Account(String username, String name, String email, String password, AccountRole role, String comment) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.comment = comment;
    }

    public static Account createAccount(AccountDto accountDto, PasswordEncoder passwordEncoder) {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .name(accountDto.getName())
                .email(accountDto.getEmail())
                .comment(accountDto.getComment())
                .password(passwordEncoder.encode(accountDto.getPassword())) //암호화
                .role(AccountRole.USER)
                .build();
        return account;
    }

}
