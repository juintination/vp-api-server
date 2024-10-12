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
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(boardRepository, "BoardRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");
        Assertions.assertNotNull(imageRepository, "ImageRepository should not be null");
        Assertions.assertNotNull(replyRepository, "ReplyRepository should not be null");

        log.info(boardRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
        log.info(imageRepository.getClass().getName());
        log.info(replyRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsert() {

        Image image = imageRepository.save(Image.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .build());

        Member writer = memberRepository.save(Member.builder()
                .email(faker.internet().emailAddress())
                .password(passwordEncoder.encode(faker.internet().password()))
                .nickname(faker.name().name())
                .memberRole(MemberRole.USER)
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
                    .email(faker.internet().emailAddress())
                    .password(passwordEncoder.encode(faker.internet().password()))
                    .nickname(faker.name().name())
                    .memberRole(MemberRole.USER)
                    .build());

            Reply reply = Reply.builder()
                    .content("TestReply" + i)
                    .board(savedBoard)
                    .replier(member)
                    .build();

            Reply result = replyRepository.save(reply);
            log.info("bno: " + savedBoard.getBno());
            log.info(result);
        }

    }

    @Test
    @Transactional
    public void testRead() {
        Long id = 1L;
        Optional<Reply> result = replyRepository.findById(id);
        Reply reply = result.orElseThrow();

        Assertions.assertNotNull(reply);
        log.info(reply);
        log.info(reply.getBoard());
        log.info(reply.getReplier());
    }

    @Test
    public void testReadListByBoard() {
        Long bno = 1L;
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        replyList.forEach(log::info);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        replyRepository.deleteById(id);
        Optional<Reply> result = replyRepository.findById(id);

        Assertions.assertEquals(result, Optional.empty());
        log.info(result);
    }

    @Test
    public void testDeleteByBoard() {
        Long bno = 1L;
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
        boardRepository.deleteById(bno);

        replies.forEach(reply -> {
            Optional<Reply> result = replyRepository.findById(reply.getRno());
            Assertions.assertEquals(result, Optional.empty());
            log.info(result);
        });
    }
}
