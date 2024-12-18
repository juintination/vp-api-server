package com.example.vpapi.service;

import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.dto.MemberDTO;
import com.example.vpapi.dto.VideoDTO;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VideoServiceTests {

    @Autowired
    private VideoService videoService;

    @Autowired
    private MemberService memberService;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(videoService, "BoardService should not be null");
        Assertions.assertNotNull(memberService, "MemberService should not be null");

        log.info(videoService.getClass().getName());
        log.info(memberService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        MemberDTO uploaderDTO = MemberDTO.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .nickname(faker.name().name())
                .role(MemberRole.USER)
                .build();
        log.info(uploaderDTO);

        Long uno = memberService.register(uploaderDTO);
        log.info(uno);
        log.info(memberService.get(uno));

        VideoDTO videoDTO = VideoDTO.builder()
                .uno(uno)
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
