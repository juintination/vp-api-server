package com.example.vpapi.service;

import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.dto.*;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReplyServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReplyService replyService;

    private final Faker faker = new Faker();

    @BeforeAll
    public void setup() {
        Assertions.assertNotNull(boardService, "BoardService should not be null");
        Assertions.assertNotNull(imageService, "ImageService should not be null");
        Assertions.assertNotNull(memberService, "MemberService should not be null");
        Assertions.assertNotNull(replyService, "ReplyService should not be null");

        log.info(boardService.getClass().getName());
        log.info(imageService.getClass().getName());
        log.info(memberService.getClass().getName());
        log.info(replyService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        MemberDTO writerDTO = MemberDTO.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .nickname(faker.name().name())
                .role(MemberRole.USER)
                .build();
        log.info(writerDTO);

        Long wno = memberService.register(writerDTO);
        log.info(wno);
        log.info(memberService.get(wno));

        ImageDTO imageDTO = ImageDTO.builder()
                .uno(wno)
                .fileName(UUID.randomUUID() + "_" + "IMAGE0.png")
                .build();

        Long ino = imageService.register(imageDTO);
        log.info(ino);
        log.info(imageService.get(ino));

        BoardDTO boardDTO = BoardDTO.builder()
                .title("SampleTitle")
                .content("SampleContent")
                .ino(ino)
                .writerId(wno)
                .writerEmail(writerDTO.getEmail())
                .writerNickname(writerDTO.getNickname())
                .build();
        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);
        log.info(bno);
        log.info(boardService.get(bno));
        log.info(imageService.dtoToEntity(imageService.get(ino)));

        for (int i = 0; i < 5; i++) {
            MemberDTO memberDTO = MemberDTO.builder()
                    .email(faker.internet().emailAddress())
                    .password(faker.internet().password())
                    .nickname(faker.name().name())
                    .role(MemberRole.USER)
                    .build();

            Long mno = memberService.register(memberDTO);
            log.info(mno);
            log.info(memberService.get(mno));

            ReplyDTO replyDTO = ReplyDTO.builder()
                    .bno(bno)
                    .replierId(mno)
                    .content("SampleContent")
                    .build();

            Long rno = replyService.register(replyDTO);
            log.info(rno);
            log.info(replyService.get(rno));
        }

    }

    @Test
    public void testGet() {
        Long rno = 1L;
        ReplyDTO replyDTO = replyService.get(rno);
        Assertions.assertNotNull(replyDTO);
        log.info(replyDTO);
    }

    @Test
    public void testGetListWithBoard() {
        Long rno = 1L;
        List<ReplyDTO> replies = replyService.getRepliesByBoard(rno);
        log.info("replies: " + replies);

        replies.forEach(Assertions::assertNotNull);
    }

    @Test
    public void testRemove() {
        Long rno = 1L;
        replyService.remove(rno);;
        Assertions.assertThrows(RuntimeException.class, () -> replyService.get(rno));
    }

    @Test
    public void testDeleteByBoard() {
        Long bno = 1L;
        boardService.remove(bno);

        List<ReplyDTO> deletedReplies = replyService.getRepliesByBoard(bno);
        Assertions.assertTrue(deletedReplies.isEmpty(), "Replies were not deleted");
    }
}
