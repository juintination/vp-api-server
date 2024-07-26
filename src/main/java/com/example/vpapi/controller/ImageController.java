package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.service.ImageService;
import com.example.vpapi.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final CustomFileUtil fileUtil;

    @GetMapping("/{ino}")
    public ImageDTO read(@PathVariable("ino") Long ino) {
        return imageService.get(ino);
    }

    @GetMapping("/view/{ino}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("ino") Long ino) {
        String fileName = read(ino).getFileName();
        return fileUtil.getFile(fileName);
    }

    @PostMapping("/")
    public Map<String, Long> register(ImageDTO imageDTO) throws IOException {
        MultipartFile file = imageDTO.getFile();
        String uploadFileName = fileUtil.saveFile(file);
        imageDTO.setFileName(uploadFileName);
        log.info("-------------------------------------");
        log.info(uploadFileName);
        Long ino = imageService.register(imageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("RESULT", ino);
    }

    @DeleteMapping("/{ino}")
    public Map<String, String> remove(@PathVariable(name="ino") Long pno) {
        String oldFileName = imageService.get(pno).getFileName();
        imageService.remove(pno);
        fileUtil.deleteFile(oldFileName);
        return Map.of("RESULT", "SUCCESS");
    }

}
