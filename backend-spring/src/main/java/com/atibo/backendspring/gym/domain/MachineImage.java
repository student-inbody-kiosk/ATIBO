package com.atibo.backendspring.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MachineImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    public MachineImage() {
    }

    public MachineImage(Machine machine, String image) {
        this.machine = machine;
        this.imagePath = image;
    }
}
