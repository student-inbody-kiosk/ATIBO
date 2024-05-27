package com.atibo.backendspring.gym.application;

import com.atibo.backendspring.gym.domain.Machine;
import com.atibo.backendspring.gym.domain.MachineImage;
import com.atibo.backendspring.gym.dto.MachineDto;
import com.atibo.backendspring.gym.dto.MachineImageRequest;
import com.atibo.backendspring.gym.dto.MachineImageResponse;
import com.atibo.backendspring.gym.repository.MachineImageRepository;
import com.atibo.backendspring.gym.repository.MachineRepository;
import com.atibo.backendspring.school.application.SchoolService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MachineService {
    private static final String UPLOAD_DIR = "src/main/resources/static/machine/";
    private final MachineRepository machineRepository;
    private final MachineImageRepository machineImageRepository;

    public MachineService(MachineRepository machineRepository, MachineImageRepository machineImageRepository) {
        this.machineRepository = machineRepository;
        this.machineImageRepository = machineImageRepository;
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

    @Transactional
    public MachineDto updateMachine(Integer gymId, MachineDto request) {
        Machine machine = machineRepository.findMachineById(gymId);
        machine.update(request.getName(), request.getDescription());
        return new MachineDto(machine);
    }

    public void deleteMachine(Integer gymId) {
        Machine machine = machineRepository.findMachineById(gymId);
        Set<MachineImage> machineImageSet = machine.getImageSet();
        // 실제 파일 지우기
        for (MachineImage machineImage : machineImageSet) {
            deleteImage(machineImage);
        }
        machineRepository.delete(machine);
    }

    public void registerImage(Integer gymId, MachineImageRequest request) {
        Machine machine = machineRepository.findMachineById(gymId);
        MachineImage machineImage = new MachineImage(machine, request.getImages());
        machineImageRepository.save(machineImage);
    }

    public List<MachineImageResponse> getMachineImageList(Integer gymId) {
        List<MachineImage> machineImageList = machineImageRepository.findByMachineId(gymId);
        List<MachineImageResponse> response = new ArrayList<>();
        for (MachineImage machineImage : machineImageList) {
            response.add(new MachineImageResponse(machineImage));
        }
        return response;
    }

    public void deleteRemainImages(Integer gymId, Map<String, Object> paramMap) {
        Machine machine = machineRepository.findMachineById(gymId);
        Set<MachineImage> machineImages = machine.getImageSet();
        List<Integer> imageIds = getImageIds(paramMap);
        Set<Integer> idsToDelete = getDeleteIds(machineImages, imageIds);
        deleteImages(machine, idsToDelete);
    }

    @Transactional
    public void deleteImages(Machine machine, Set<Integer> idsToDelete) {
        for (Integer id : idsToDelete) {
            MachineImage machineImage = machineImageRepository.findMachineImageById(id);
            deleteImage(machineImage);
            machine.getImageSet().remove(machineImage);
            machineRepository.save(machine);
        }
    }

    public List<Integer> getImageIds(Map<String, Object> paramMap) {
        List<String> keys = new ArrayList<>(paramMap.keySet());
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < keys.size(); i += 2) {
            String idKey = keys.get(i);
            Object idValue = paramMap.get(idKey);
            int id = Integer.parseInt(idValue.toString());
            ids.add(id);
        }
        return ids;
    }

    public Set<Integer> getDeleteIds(Set<MachineImage> machineImages, List<Integer> remainIds) {
        return machineImages.stream()
                            .map(MachineImage::getId)
                            .filter(id -> !remainIds.contains(id))
                            .collect(Collectors.toSet());
    }

    public void createImages(Integer gymId, Map<String, MultipartFile> fileMap) {
        List<MultipartFile> images = getImages(fileMap);
        Machine machine = machineRepository.findMachineById(gymId);
        for (MultipartFile image : images) {
            createImage(machine, image);
        }
    }

    private List<MultipartFile> getImages(Map<String, MultipartFile> fileMap) {
        return new ArrayList<>(fileMap.values());
    }

    public void deleteImage(MachineImage machineImage) {
        String filePath = machineImage.getImagePath();
        filePath = filePath.substring(SchoolService.LOCAL_DIR.length());
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createImage(Machine machine, MultipartFile image) {
        try {
            // 디렉토리가 존재하지 않으면 생성
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = image.getOriginalFilename();
            assert fileName != null;
            Path filePath = uploadPath.resolve(fileName);

            if (!Files.exists(filePath)) {
                Files.copy(image.getInputStream(), filePath);
            }

            MachineImage data = new MachineImage(machine, SchoolService.LOCAL_DIR + filePath.toString());
            machineImageRepository.save(data);

        } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}