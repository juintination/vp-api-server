package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.VideoDTO;
import com.example.vpapi.service.ConvertService;
import com.example.vpapi.service.ImageService;
import com.example.vpapi.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/convert")
public class ConvertController {

    private final ImageService imageService;
    private final VideoService videoService;
    private final ConvertService convertService;

    @GetMapping("/{vno}")
    public Map<String, Long> convertFile(@PathVariable Long vno) {
        VideoDTO videoDTO = videoService.get(vno);
        ImageDTO imageDTO = convertService.uploadAndConvertFile(videoDTO);
        Long ino = imageService.register(imageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("ino", ino);
    }

}
