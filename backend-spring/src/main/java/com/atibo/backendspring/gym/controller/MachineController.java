package com.atibo.backendspring.gym.controller;

import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.gym.application.MachineService;
import com.atibo.backendspring.gym.dto.MachineDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/gym/")
    public ResponseEntity<List<MachineDto>> searchMachineList() {
        System.out.println("운동기구 목록 조회");
        List<MachineDto> response = machineService.searchMachineList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/gym/{gymId}/")
    public ResponseEntity<MachineDto> searchMachine(@PathVariable Integer gymId) {
        System.out.println("운동기구 디테일 조회");
        MachineDto response = machineService.searchMachine(gymId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/gym/{gymId}/")
    public ResponseEntity<MachineDto> updateMachine(@PathVariable Integer gymId,
                                                    @RequestBody MachineDto request) {
        System.out.println("운동기구 디테일 수정");
        MachineDto response = machineService.updateMachine(gymId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/gym/{gymId}/")
    public ResponseEntity<Response> deleteMachine(@PathVariable Integer gymId) {
        System.out.println("운동기구 디테일 삭제");
        machineService.deleteMachine(gymId);
        Response response = new Response("Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
