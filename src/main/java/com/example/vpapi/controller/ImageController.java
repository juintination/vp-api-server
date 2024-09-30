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
import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final CustomFileUtil fileUtil;

    @GetMapping("/{ino}")
    public ImageDTO get(@PathVariable("ino") Long ino) {
        return imageService.get(ino);
    }

    @GetMapping("/view/{ino}")
    public Map<String, String> viewFileGET(@PathVariable("ino") Long ino) throws IOException {
        String fileName = get(ino).getFileName();
        Resource fileResource = fileUtil.getFile(fileName).getBody();
        assert fileResource != null; // "error": "NOT_EXIST_IMAGE"
        byte[] fileContent = fileUtil.getFileContent(fileResource);
        String base64FileContent = Base64.getEncoder().encodeToString(fileContent);
        return Map.of("fileContent", base64FileContent);
    }

    @GetMapping("/view/thumbnail/{ino}")
    public ResponseEntity<Resource> viewThumbnailGET(@PathVariable("ino") Long ino) {
        String fileName = "s_" + get(ino).getFileName();
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

        return Map.of("ino", ino);
    }

    @DeleteMapping("/{ino}")
    public Map<String, String> remove(@PathVariable(name="ino") Long ino) {
        String oldFileName = imageService.get(ino).getFileName();
        imageService.remove(ino);
        fileUtil.deleteFile(oldFileName);
        return Map.of("RESULT", "SUCCESS");
    }

}
