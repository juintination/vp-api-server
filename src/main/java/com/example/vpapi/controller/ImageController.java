package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.facade.ImageFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/images")
public class ImageController {

    private final ImageFacade imageFacade;

    @GetMapping("/{ino}")
    public ImageDTO get(@PathVariable("ino") Long ino) {
        return imageFacade.getImage(ino);
    }

    @GetMapping("/view/{ino}")
    public Map<String, String> viewFileGET(@PathVariable("ino") Long ino) throws IOException {
        return imageFacade.viewImage(ino);
    }

    @GetMapping("/view/thumbnail/{ino}")
    public Map<String, String> viewThumbnailGET(@PathVariable("ino") Long ino) throws IOException {
        return imageFacade.viewImageThumbnail(ino);
    }

    @PostMapping("/")
    @PreAuthorize("#imageDTO.uno == authentication.principal.mno")
    public Map<String, Long> register(ImageDTO imageDTO) throws IOException {
        return imageFacade.registerImage(imageDTO);
    }

    @DeleteMapping("/{ino}")
    public Map<String, String> remove(@PathVariable(name="ino") Long ino) {
        imageFacade.removeImage(ino);
        return Map.of("RESULT", "SUCCESS");
    }

}
