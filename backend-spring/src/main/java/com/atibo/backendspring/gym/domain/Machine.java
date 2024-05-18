package com.atibo.backendspring.gym.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;
    @Size(min = 2, message = "Ensure this field has at least 2 characters")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Machine() {
    }

    public Machine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
