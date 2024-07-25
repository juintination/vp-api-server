package com.example.vpapi.repository;

import com.example.vpapi.domain.Image;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ImageRepositoryTests {

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(imageRepository);
        log.info(imageRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        for (int i = 1; i <= 10; i++) {
            Image image = Image.builder()
                    .fileName(UUID.randomUUID() + "_" + "IMAGE" + i + ".png")
                    .build();
            Image result = imageRepository.save(image);
            log.info(result);
        }

    }

    @Test
    public void testRead() {

        Long ino = 1L;
        Optional<Image> result = imageRepository.findById(ino);
        Image image = result.orElseThrow();

        Assertions.assertNotNull(image);
        log.info(image);

    }

    @Test
    public void testDelete() {

        Long ino = 1L;
        imageRepository.deleteById(ino);
        Optional<Image> result = imageRepository.findById(ino);

        Assertions.assertEquals(result, Optional.empty());
        log.info(result);

    }

}
