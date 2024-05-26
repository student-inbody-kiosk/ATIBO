package com.atibo.backendspring.gym.dto;

import com.atibo.backendspring.gym.domain.MachineImage;

import lombok.Getter;

@Getter
public class MachineImageResponse {
    private int id;
    private String image;

    public MachineImageResponse() {
    }

    public MachineImageResponse(MachineImage machineImage) {
        this.id = machineImage.getId();
        this.image = machineImage.getImagePath();
    }
}
