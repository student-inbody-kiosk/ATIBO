package com.atibo.backendspring.gym.controller;

import com.atibo.backendspring.gym.application.MachineService;
import com.atibo.backendspring.gym.dto.MachineDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping("/api/gym/")
    public ResponseEntity<MachineDto> createMachineDetail(@RequestBody MachineDto.MachineRequest request) {
        System.out.println("운동기구 디테일 생성");
        MachineDto response = machineService.createDetail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
