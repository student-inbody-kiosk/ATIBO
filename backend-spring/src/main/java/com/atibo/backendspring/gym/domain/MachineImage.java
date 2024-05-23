package com.atibo.backendspring.gym.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class MachineImage {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;
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
