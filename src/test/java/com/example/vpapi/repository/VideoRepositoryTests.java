package com.example.vpapi.repository;

import com.example.vpapi.domain.Video;
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
public class VideoRepositoryTests {

    @Autowired
    private VideoRepository videoRepository;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(videoRepository);
        log.info(videoRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        for (int i = 1; i <= 10; i++) {
            Video video = Video.builder()
                    .fileName(UUID.randomUUID() + "_" + "VIDEO" + i + ".mp4")
                    .build();
            Video result = videoRepository.save(video);
            log.info(result);
        }

    }

    @Test
    public void testRead() {

        Long vno = 1L;
        Optional<Video> result = videoRepository.findById(vno);
        Video video = result.orElseThrow();

        Assertions.assertNotNull(video);
        log.info(video);

    }

    @Test
    public void testDelete() {

        Long vno = 1L;
        videoRepository.deleteById(vno);
        Optional<Video> result = videoRepository.findById(vno);

        Assertions.assertEquals(result, Optional.empty());
        log.info(result);

    }

}
