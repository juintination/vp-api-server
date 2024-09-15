package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.BoardDTO;
import com.example.vpapi.dto.PageRequestDTO;
import com.example.vpapi.dto.PageResponseDTO;
import com.example.vpapi.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ImageService imageService;

    private final MemberService memberService;

    @Override
    public BoardDTO get(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        return entityToDTO(board);
    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

        Page<Board> result = boardRepository.getPagedBoards(pageRequestDTO);

        List<BoardDTO> dtoList = result
                .get()
                .map(this::entityToDTO).
                collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO);
        Board result = boardRepository.save(board);
        return result.getBno();
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public Board dtoToEntity(BoardDTO boardDTO) {
        Member writer = memberService.dtoToEntity(memberService.get(boardDTO.getWriterId()));
        Image image = imageService.dtoToEntity(imageService.get(boardDTO.getIno()));
        return Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(writer)
                .image(image)
                .build();
    }

}
