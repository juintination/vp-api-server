package com.example.vpapi.service;

import com.example.vpapi.domain.MemberRole;
import com.example.vpapi.dto.*;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReplyService replyService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(boardService, "BoardRepository should not be null");
        Assertions.assertNotNull(imageService, "ImageRepository should not be null");
        Assertions.assertNotNull(memberService, "MemberRepository should not be null");
        Assertions.assertNotNull(replyService, "ReplyRepository should not be null");

        log.info(boardService.getClass().getName());
        log.info(imageService.getClass().getName());
        log.info(memberService.getClass().getName());
        log.info(replyService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {
        ImageDTO imageDTO = ImageDTO.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE0.png")
                .build();

        Long ino = imageService.register(imageDTO);
        log.info(ino);
        log.info(imageService.get(ino));

        MemberDTO writerDTO = MemberDTO.builder()
                .email(new Faker().internet().emailAddress())
                .password("1234")
                .nickname("SampleUser")
                .role(MemberRole.USER)
                .build();
        log.info(writerDTO);

        Long wno = memberService.register(writerDTO);
        log.info(wno);
        log.info(memberService.get(wno));

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
                    .email(new Faker().internet().emailAddress())
                    .password("1234")
                    .nickname("SampleUser" + i)
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