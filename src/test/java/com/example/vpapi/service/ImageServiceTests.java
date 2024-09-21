package com.example.vpapi.service;

import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.MemberDTO;
import com.github.javafaker.Faker;
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

    @Autowired
    private MemberService memberService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(imageService, "BoardRepository should not be null");
        Assertions.assertNotNull(memberService, "MemberRepository should not be null");

        log.info(imageService.getClass().getName());
        log.info(memberService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        MemberDTO uploaderDTO = MemberDTO.builder()
                .email(new Faker().internet().emailAddress())
                .password("1234")
                .nickname("SampleUser")
                .role(MemberRole.USER)
                .build();
        log.info(uploaderDTO);

        Long uno = memberService.register(uploaderDTO);
        log.info(uno);
        log.info(memberService.get(uno));

        ImageDTO imageDTO = ImageDTO.builder()
                .uno(uno)
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
