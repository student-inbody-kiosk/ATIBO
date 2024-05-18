package com.atibo.backendspring.gym.application;

import com.atibo.backendspring.gym.domain.Machine;
import com.atibo.backendspring.gym.dto.MachineDto;
import com.atibo.backendspring.gym.repository.MachineRepository;

import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MachineDto> searchMachineList() {
        List<Machine> machineList = machineRepository.findAll();
        return machineList.stream().map(MachineDto::new).toList();
    }

    public MachineDto searchMachine(Integer gymId) {
        Machine data = machineRepository.findMachineById(gymId);
        return new MachineDto(data);
    }

    public MachineDto updateMachine(Integer gymId, MachineDto request) {
        Machine machine = machineRepository.findMachineById(gymId);
        machine.update(request.getName(), request.getDescription());
        return new MachineDto(machine);
    }
}
