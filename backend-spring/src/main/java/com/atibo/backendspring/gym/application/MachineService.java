package com.atibo.backendspring.gym.application;

import com.atibo.backendspring.gym.domain.Machine;
import com.atibo.backendspring.gym.dto.MachineDto;
import com.atibo.backendspring.gym.repository.MachineRepository;

import org.springframework.stereotype.Service;

@Service
public class MachineService {
    private MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public MachineDto createDetail(MachineDto.MachineRequest request) {
        Machine data = request.convertToEntity();
        machineRepository.save(data);
        return new MachineDto(data);
    }
}
