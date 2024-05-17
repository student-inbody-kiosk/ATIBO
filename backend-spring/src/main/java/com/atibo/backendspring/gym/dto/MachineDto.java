package com.atibo.backendspring.gym.dto;

import com.atibo.backendspring.gym.domain.Machine;

import lombok.Getter;

@Getter
public class MachineDto {
    private Integer id;
    private String name;
    private String description;

    public MachineDto() {
    }

    public MachineDto(Machine machine) {
        this.id = machine.getId();
        this.name = machine.getName();
        this.description = machine.getDescription();
    }

    @Getter
    public static class MachineRequest {
        private String name;
        private String description;

        public MachineRequest() {
        }

        public Machine convertToEntity() {
            return new Machine(
                    this.name,
                    this.description
            );
        }
    }
}
