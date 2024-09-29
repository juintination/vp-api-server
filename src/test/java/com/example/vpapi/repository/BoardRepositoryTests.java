package com.example.vpapi.repository;

import com.example.vpapi.domain.*;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(boardRepository, "BoardRepository should not be null");
        Assertions.assertNotNull(imageRepository, "ImageRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");
        Assertions.assertNotNull(heartRepository, "HeartRepository should not be null");
        Assertions.assertNotNull(replyRepository, "ReplyRepository should not be null");

        log.info(boardRepository.getClass().getName());
        log.info(imageRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
        log.info(heartRepository.getClass().getName());
        log.info(replyRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        Member member = memberRepository.save(Member.builder()
                .email(new Faker().internet().emailAddress())
                .password(passwordEncoder.encode("1234"))
                .nickname("SampleUser")
                .memberRole(MemberRole.USER)
                .build());

        Image image = imageRepository.save(Image.builder()
                .uploader(member)
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .build());

        Board board = Board.builder()
                .title("SampleTitle")
                .content("SampleContent")
                .image(image)
                .writer(member)
                .build();

        Board savedBoard = boardRepository.save(board);

        Heart heart = Heart.builder()
                .board(savedBoard)
                .member(member)
                .build();
        heartRepository.save(heart);

        Reply reply = Reply.builder()
                .board(savedBoard)
                .replier(member)
                .content("SampleReplyContent")
                .build();
        replyRepository.save(reply);

        log.info(savedBoard);

    }

    @Test
    @Transactional
    public void testRead() {
        Long bno = 1L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        Assertions.assertNotNull(board);
        log.info(board);
    }

    @Test
    public void testReadBoardByBno() {
        Long bno = 1L;
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
        Optional<Board> result = boardRepository.findById(bno);

        Assertions.assertEquals(result, Optional.empty());
        log.info(result);
    }
}
