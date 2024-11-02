package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.service.UnifiedImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/images")
public class ImageController {

    private final UnifiedImageService unifiedImageService;

    public ImageController(@Qualifier("imageServiceAdapter") UnifiedImageService unifiedImageService) {
        this.unifiedImageService = unifiedImageService;
    }

    @GetMapping("/{ino}")
    public ImageDTO get(@PathVariable("ino") Long ino) {
        return unifiedImageService.getImage(ino);
    }

    @GetMapping("/view/{ino}")
    public Map<String, String> viewFileGET(@PathVariable("ino") Long ino) throws IOException {
        return unifiedImageService.viewImage(ino);
    }

    @GetMapping("/view/thumbnail/{ino}")
    public Map<String, String> viewThumbnailGET(@PathVariable("ino") Long ino) throws IOException {
        return unifiedImageService.viewImageThumbnail(ino);
    }

    @PostMapping("/")
    @PreAuthorize("#imageDTO.uno == authentication.principal.mno")
    public Map<String, Long> register(ImageDTO imageDTO) throws IOException {
        return unifiedImageService.registerImage(imageDTO);
    }

    @DeleteMapping("/{ino}")
    public Map<String, String> remove(@PathVariable(name="ino") Long ino) {
        unifiedImageService.removeImage(ino);
        return Map.of("RESULT", "SUCCESS");
    }

}
