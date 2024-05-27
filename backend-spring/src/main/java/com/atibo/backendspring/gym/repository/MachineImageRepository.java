package com.atibo.backendspring.gym.repository;
import com.atibo.backendspring.gym.domain.MachineImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineImageRepository extends JpaRepository<MachineImage, Integer> {
    List<MachineImage> findByMachineId(Integer machineId);
    MachineImage findMachineImageById(Integer id);
    void deleteById(Integer machineImageId);

}

