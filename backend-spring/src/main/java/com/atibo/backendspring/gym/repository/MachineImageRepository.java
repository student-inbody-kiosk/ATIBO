package com.atibo.backendspring.gym.repository;
import com.atibo.backendspring.gym.domain.MachineImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineImageRepository extends JpaRepository<MachineImage, Integer> {
    void deleteById(Integer machineImageId);
    List<MachineImage> findByMachineId(Integer machineId);
}

