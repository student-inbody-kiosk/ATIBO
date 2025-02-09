package com.atibo.backendspring.accounts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "username")
    @NotNull
    private String username;
    @JsonIgnore
    private String password;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "email", unique = true)
    @NotNull
    private String email;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private AccountRole role;
    private String comment;
    @JsonProperty("isActive")
    private boolean isActive;

    @Builder
    public Account(String username, String name, String email, String password, AccountRole role, String comment) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.comment = comment;
        this.isActive = false;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeActive() {
        this.isActive = true;
    }

    public void changeRole(AccountRole role) {
        this.role = role;
    }
}
