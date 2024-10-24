package com.example.vpapi.repository;

import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.domain.ProfileImage;
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
public class ProfileImageRepositoryTests {

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(profileImageRepository, "ProfileImageRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");

        log.info(profileImageRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsertProfileImage() {
        String email = "sample@example.com";
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(faker.internet().password()))
                .nickname(faker.name().name())
                .memberRole(MemberRole.USER)
                .build();
        if (!memberRepository.existsByEmail(email)) {
            memberRepository.save(member);
            ProfileImage result = profileImageRepository.save(ProfileImage.builder()
                    .member(member)
                    .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                    .build());
            log.info(result);
        }
    }

    @Test
    @Transactional
    public void testRead() {

        Long pino = 1L;
        Optional<ProfileImage> result = profileImageRepository.findById(pino);
        ProfileImage profileImage = result.orElseThrow();

        Assertions.assertNotNull(profileImage);
        log.info(profileImage);

    }

    @Test
    public void testReadProfileImageByPino() {
        Long pino = 1L;
        Object result = profileImageRepository.getProfileImageByPino(pino);
        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testDelete() {

        Long pino = 1L;
        profileImageRepository.deleteById(pino);
        Optional<ProfileImage> result = profileImageRepository.findById(pino);

        Assertions.assertEquals(Optional.empty(), result);
        log.info(result);

    }

}
