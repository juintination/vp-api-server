package com.example.vpapi.controller;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.service.ConvertService;
import com.example.vpapi.service.VideoService;
import com.example.vpapi.util.CustomFileUtil;
import com.example.vpapi.util.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
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
    private final CustomFileUtil fileUtil;

    @GetMapping("/{vno}")
    public Map<String, Long> convertFile(@PathVariable Long vno) throws IOException {
        String fileName = videoService.get(vno).getFileName();
        ResponseEntity<Resource> fileResponse = fileUtil.getFile(fileName);
        Resource fileResource = fileResponse.getBody();
        ResponseEntity<Resource> responseEntity = convertService.uploadAndConvertFile(fileResource);
        CustomMultipartFile file = new CustomMultipartFile(responseEntity);
        ImageDTO imageDTO = ImageDTO.builder()
                .fileName(file.getOriginalFilename())
                .file(file)
                .build();
        return imageController.register(imageDTO);
    }

}
