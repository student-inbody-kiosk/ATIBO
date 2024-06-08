package com.atibo.backendspring.gym.repository;

import com.atibo.backendspring.gym.domain.Machine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Integer> {
    Machine findMachineById(int id);
}
