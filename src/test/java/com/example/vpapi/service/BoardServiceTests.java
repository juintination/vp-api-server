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

import java.util.UUID;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ImageService imageService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(boardService, "BoardRepository should not be null");
        Assertions.assertNotNull(imageService, "ImageRepository should not be null");
        Assertions.assertNotNull(memberService, "MemberRepository should not be null");

        log.info(boardService.getClass().getName());
        log.info(imageService.getClass().getName());
        log.info(memberService.getClass().getName());
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

        MemberDTO memberDTO = MemberDTO.builder()
                .email(new Faker().internet().emailAddress())
                .password("1234")
                .nickname("SampleUser")
                .role(MemberRole.USER)
                .build();
        log.info(memberDTO);

        Long mno = memberService.register(memberDTO);
        log.info(mno);
        log.info(memberService.get(mno));

        BoardDTO boardDTO = BoardDTO.builder()
                .title("SampleTitle")
                .content("SampleContent")
                .ino(ino)
                .writerId(mno)
                .writerEmail(memberDTO.getEmail())
                .writerNickname(memberDTO.getNickname())
                .build();
        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);
        log.info(bno);
        log.info(boardService.get(bno));
    }

    @Test
    public void testGet() {
        Long bno = 1L;
        BoardDTO boardDTO = boardService.get(bno);
        Assertions.assertNotNull(boardDTO);
        log.info(boardDTO);
    }

    @Test
    public void testGetList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<BoardDTO> result = boardService.getList(pageRequestDTO);
        log.info(result);
    }

    @Test
    public void testModify() {
        Long bno = 1L;
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title("ModifiedTitle")
                .content("ModifiedContent")
                .build();

        boardService.modify(boardDTO);

        BoardDTO result = boardService.get(bno);
        Assertions.assertEquals("ModifiedTitle", result.getTitle());
        Assertions.assertEquals("ModifiedContent", result.getContent());
        log.info(result);
    }

    @Test
    public void testRemove() {
        Long bno = 1L;
        boardService.remove(bno);
        Assertions.assertThrows(RuntimeException.class, () -> boardService.get(bno));
    }

    @Test
    public void testRemoveWithImageCheck() {
        Long bno = 2L;
        BoardDTO boardDTO = boardService.get(bno);
        Assertions.assertNotNull(boardDTO);
        Long ino = boardDTO.getIno();

        boardService.remove(bno);
        Assertions.assertThrows(RuntimeException.class, () -> boardService.get(bno));

        ImageDTO imageDTO = imageService.get(ino);
        Assertions.assertNotNull(imageDTO);
        log.info(imageDTO);
    }
}
