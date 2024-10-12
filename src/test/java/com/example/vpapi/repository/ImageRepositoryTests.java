package com.example.vpapi.repository;

import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.MemberRole;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageRepositoryTests {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(imageRepository, "ImageRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");

        log.info(imageRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        Member uploader = memberRepository.save(Member.builder()
                .email(faker.internet().emailAddress())
                .password(passwordEncoder.encode(faker.internet().password()))
                .nickname(faker.name().name())
                .memberRole(MemberRole.USER)
                .build());

        Image result = imageRepository.save(Image.builder()
                .uploader(uploader)
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .build());
        log.info(result);

    }

    @Test
    @Transactional
    public void testRead() {

        Long ino = 1L;
        Optional<Image> result = imageRepository.findById(ino);
        Image image = result.orElseThrow();

        Assertions.assertNotNull(image);
        log.info(image);

    }

    @Test
    public void testReadImageByIno() {
        Long ino = 1L;
        Object result = imageRepository.getImageByIno(ino);
        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));
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
