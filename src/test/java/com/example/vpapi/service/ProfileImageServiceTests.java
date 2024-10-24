package com.example.vpapi.service;

import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.dto.MemberDTO;
import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.util.CustomServiceException;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileImageServiceTests {

    @Autowired
    private ProfileImageService profileImageService;

    @Autowired
    private MemberService memberService;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(profileImageService, "ProfileImageService should not be null");
        Assertions.assertNotNull(memberService, "MemberService should not be null");

        log.info(profileImageService.getClass().getName());
        log.info(memberService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        MemberDTO memberDTO = MemberDTO.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .nickname(faker.name().name())
                .role(MemberRole.USER)
                .build();
        log.info(memberDTO);

        Long mno = memberService.register(memberDTO);

        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .mno(mno)
                .fileName(UUID.randomUUID() + "_" + "IMAGE0.png")
                .build();
        Long pino = profileImageService.register(profileImageDTO);
        log.info(pino);

    }

    @Test
    public void testGet() {
        Long pino = 1L;
        ProfileImageDTO profileImageDTO = profileImageService.get(pino);
        Assertions.assertNotNull(profileImageDTO);
        log.info(profileImageDTO);
    }

    @Test
    public void testRemove() {
        Long pino = 1L;
        profileImageService.remove(pino);
        Assertions.assertThrows(CustomServiceException.class, () -> profileImageService.get(pino));
    }

}
