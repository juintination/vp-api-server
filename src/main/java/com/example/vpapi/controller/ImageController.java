package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.service.adapter.UnifiedImageServiceAdapter;
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

    private final UnifiedImageServiceAdapter unifiedImageServiceAdapter;

    public ImageController(@Qualifier("imageServiceAdapter") UnifiedImageServiceAdapter unifiedImageServiceAdapter) {
        this.unifiedImageServiceAdapter = unifiedImageServiceAdapter;
    }

    @GetMapping("/{ino}")
    public ImageDTO get(@PathVariable("ino") Long ino) {
        return unifiedImageServiceAdapter.getImage(ino);
    }

    @GetMapping("/view/{ino}")
    public Map<String, String> viewFileGET(@PathVariable("ino") Long ino) throws IOException {
        return unifiedImageServiceAdapter.viewImage(ino);
    }

    @GetMapping("/view/thumbnail/{ino}")
    public Map<String, String> viewThumbnailGET(@PathVariable("ino") Long ino) throws IOException {
        return unifiedImageServiceAdapter.viewImageThumbnail(ino);
    }

    @PostMapping("/")
    @PreAuthorize("#imageDTO.uno == authentication.principal.mno")
    public Map<String, Long> register(ImageDTO imageDTO) throws IOException {
        return unifiedImageServiceAdapter.registerImage(imageDTO);
    }

    @DeleteMapping("/{ino}")
    public Map<String, String> remove(@PathVariable(name="ino") Long ino) {
        unifiedImageServiceAdapter.removeImage(ino);
        return Map.of("RESULT", "SUCCESS");
    }

}
