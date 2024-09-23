package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.VideoDTO;
import com.example.vpapi.service.ConvertService;
import com.example.vpapi.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/convert")
public class ConvertController {

    private final ImageController imageController;
    private final VideoService videoService;
    private final ConvertService convertService;

    @GetMapping("/{vno}")
    public Map<String, Long> convertFile(@PathVariable Long vno) throws IOException {
        VideoDTO videoDTO = videoService.get(vno);
        ImageDTO imageDTO = convertService.uploadAndConvertFile(videoDTO);
        log.info("Converted: {}", imageDTO);
        return imageController.register(imageDTO);
    }

}
