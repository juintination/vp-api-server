package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.BoardDTO;
import com.example.vpapi.dto.PageRequestDTO;
import com.example.vpapi.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardService {

    BoardDTO get(Long bno);

    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(BoardDTO boardDTO);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    Board dtoToEntity(BoardDTO boardDTO);

    default BoardDTO entityToDTO(Board board, Member writer, Image image, int replyCount, int heartCount) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .ino(image != null ? image.getIno() : null)
                .writerId(writer.getMno())
                .writerEmail(writer.getEmail())
                .writerNickname(writer.getNickname())
                .replyCount(replyCount)
                .heartCount(heartCount)
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
    }

}
