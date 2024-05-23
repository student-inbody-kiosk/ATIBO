package com.atibo.backendspring.gym.repository;
import com.atibo.backendspring.gym.domain.MachineImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineImageRepository extends JpaRepository<MachineImage, Integer> {
}

