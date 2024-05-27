package com.atibo.backendspring.gym.controller;

import com.atibo.backendspring.accounts.dto.Response;
import com.atibo.backendspring.gym.application.MachineService;
import com.atibo.backendspring.gym.dto.MachineDto;
import com.atibo.backendspring.gym.dto.MachineImageRequest;
import com.atibo.backendspring.gym.dto.MachineImageResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.atibo.backendspring.school.controller.SchoolController.FILE_DIRECTORY;

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

    @GetMapping("/api/gym/{gymId}/image/")
    public ResponseEntity<List<MachineImageResponse>> getMachineImageList(@PathVariable Integer gymId) {
        System.out.println("운동기구 사진 조회");
        List<MachineImageResponse> response = machineService.getMachineImageList(gymId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/gym/{gymId}/image/")
    public ResponseEntity<List<MachineImageResponse>> changeMachineImageList(@PathVariable Integer gymId,
                                                    @RequestParam Map<String, Object> paramMap,
                                                    @RequestParam Map<String, MultipartFile> fileMap)  {
        machineService.deleteRemainImages(gymId, paramMap);
        machineService.createImages(gymId, fileMap);
        List<MachineImageResponse> response = machineService.getMachineImageList(gymId);
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

    @PostMapping("/api/gym/{gymId}/image")
    public ResponseEntity downLoadImage(@PathVariable Integer gymId,
                                        @RequestBody MachineImageRequest request) {
        System.out.println("운동기구 사진 등록");
        machineService.registerImage(gymId, request);
        return ResponseEntity.ok("Image saved successfully");
    }

    @GetMapping("/src/main/resources/static/machine/{filePath:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filePath) {
        System.out.println("이미지 전송");
        Path fileLocation = Paths.get(FILE_DIRECTORY+"/machine").resolve(filePath);
        Resource resource;
        try {
            resource = new UrlResource(fileLocation.toUri());
        } catch (MalformedURLException e) {
            // Handle malformed URL exception
            return ResponseEntity.notFound().build();
        }
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                                 .body(resource);
        } else {
            // Handle file not found or unreadable
            return ResponseEntity.notFound().build();
        }
    }
}
