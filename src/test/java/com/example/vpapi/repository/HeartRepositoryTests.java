package com.example.vpapi.repository;

import com.example.vpapi.domain.*;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeartRepositoryTests {

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(boardRepository, "BoardRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");
        Assertions.assertNotNull(imageRepository, "ImageRepository should not be null");
        Assertions.assertNotNull(heartRepository, "HeartRepository should not be null");

        log.info(boardRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
        log.info(imageRepository.getClass().getName());
        log.info(heartRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        Member writer = memberRepository.save(Member.builder()
                .email(new Faker().internet().emailAddress())
                .password(passwordEncoder.encode("1234"))
                .nickname("SampleUser")
                .memberRole(MemberRole.USER)
                .build());

        Image image = imageRepository.save(Image.builder()
                .uploader(writer)
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .build());

        Board board = Board.builder()
                .title("SampleTitle")
                .content("SampleContent")
                .image(image)
                .writer(writer)
                .build();
        Board savedBoard = boardRepository.save(board);

        for (int i = 0; i < 5; i++) {
            Member member = memberRepository.save(Member.builder()
                    .email(new Faker().internet().emailAddress())
                    .password(passwordEncoder.encode("1234"))
                    .nickname("SampleUser" + i)
                    .memberRole(MemberRole.USER)
                    .build());

            Heart heart = Heart.builder()
                    .board(savedBoard)
                    .member(member)
                    .build();

            Heart result = heartRepository.save(heart);
            log.info("bno: " + savedBoard.getBno());
            log.info(result);
        }

    }

    @Test
    @Transactional
    public void testRead() {
        Long hno = 1L;
        Optional<Heart> result = heartRepository.findById(hno);
        Heart heart = result.orElseThrow();

        Assertions.assertNotNull(heart);
        log.info(heart);
        log.info(heart.getBoard());
        log.info(heart.getMember());
    }

    @Test
    public void testReadListByBoard() {
        Long bno = 1L;
        List<Heart> replyList = heartRepository.getHeartsByBoardOrderByHno(Board.builder().bno(bno).build());
        replyList.forEach(log::info);
    }

    @Test
    public void testDelete() {
        Long hno = 1L;
        heartRepository.deleteById(hno);
        Optional<Heart> result = heartRepository.findById(hno);

        Assertions.assertEquals(result, Optional.empty());
        log.info(result);
    }

    @Test
    public void testDeleteByBoard() {
        Long bno = 1L;
        List<Heart> hearts = heartRepository.getHeartsByBoardOrderByHno(Board.builder().bno(bno).build());
        boardRepository.deleteById(bno);

        hearts.forEach(heart -> {
            Optional<Heart> result = heartRepository.findById(heart.getHno());
            Assertions.assertEquals(result, Optional.empty());
            log.info(result);
        });
    }
}
