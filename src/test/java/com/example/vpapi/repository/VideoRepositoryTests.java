package com.example.vpapi.repository;

import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.domain.Video;
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
public class VideoRepositoryTests {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(videoRepository, "VideoRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");

        log.info(videoRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        Member uploader = memberRepository.save(Member.builder()
                .email(new Faker().internet().emailAddress())
                .password(passwordEncoder.encode("1234"))
                .nickname("SampleUser")
                .memberRole(MemberRole.USER)
                .build());

        Video result = videoRepository.save(Video.builder()
                .uploader(uploader)
                .fileName(UUID.randomUUID() + "_" + "VIDEO.mp4")
                .build());
        log.info(result);

    }

    @Test
    @Transactional
    public void testRead() {

        Long vno = 1L;
        Optional<Video> result = videoRepository.findById(vno);
        Video video = result.orElseThrow();

        Assertions.assertNotNull(video);
        log.info(video);

    }

    @Test
    public void testReadVideoByVno() {
        Long vno = 1L;
        Object result = videoRepository.getVideoByVno(vno);
        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));
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
