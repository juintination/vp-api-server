package com.example.vpapi.service;

import com.example.vpapi.dto.VideoDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class VideoServiceTests {

    @Autowired
    private VideoService videoService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(videoService);
        log.info(videoService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {
        VideoDTO videoDTO = VideoDTO.builder()
                .fileName(UUID.randomUUID() + "_" + "VIDEO0.mp4")
                .build();
        Long vno = videoService.register(videoDTO);
        log.info(vno);
    }

    @Test
    public void testGet() {
        Long vno = 1L;
        VideoDTO videoDTO = videoService.get(vno);
        Assertions.assertNotNull(videoDTO);
        log.info(videoDTO);
    }

}
