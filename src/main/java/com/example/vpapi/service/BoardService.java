package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
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

    default BoardDTO entityToDTO(Board board) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .ino(board.getImage() != null ? board.getImage().getIno() : null)
                .writerId(board.getWriter().getMno())
                .writerEmail(board.getWriter().getEmail())
                .writerNickname(board.getWriter().getNickname())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
    }

}
