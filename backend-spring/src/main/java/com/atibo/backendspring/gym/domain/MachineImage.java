package com.atibo.backendspring.gym.domain;

import com.atibo.backendspring.students.domain.Student;

import jakarta.persistence.*;

@Entity
public class MachineImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    public MachineImage() {
    }

    public MachineImage(Machine machine, String image) {
        this.machine = machine;
        this.image = image;
    }
}
