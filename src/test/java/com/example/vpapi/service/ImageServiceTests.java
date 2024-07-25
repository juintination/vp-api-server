package com.example.vpapi.service;

import com.example.vpapi.dto.ImageDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class ImageServiceTests {

    @Autowired
    private ImageService imageService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(imageService);
        log.info(imageService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {
        ImageDTO imageDTO = ImageDTO.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE0.png")
                .build();
        Long ino = imageService.register(imageDTO);
        log.info(ino);
    }

    @Test
    public void testGet() {
        Long ino = 1L;
        ImageDTO imageDTO = imageService.get(ino);
        Assertions.assertNotNull(imageDTO);
        log.info(imageDTO);
    }

}
