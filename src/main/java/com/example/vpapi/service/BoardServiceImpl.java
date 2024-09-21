package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.BoardDTO;
import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.PageRequestDTO;
import com.example.vpapi.dto.PageResponseDTO;
import com.example.vpapi.repository.BoardRepository;
import com.example.vpapi.util.CustomServiceException;
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
        Object result = boardRepository.getBoardByBno(bno);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_BOARD");
        }
        Object[] arr = (Object[]) result;
        return entityToDTO((Board) arr[0], (Member) arr[1], (Image) arr[2], ((Number) arr[3]).intValue(), ((Number) arr[4]).intValue());
    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO, Long writerId) {

        Page<Object[]> result;
        if (writerId == null) {
            result = boardRepository.getPagedBoards(pageRequestDTO);
        } else {
            result = boardRepository.getPagedBoardsByWriterId(pageRequestDTO, writerId);
        }

        List<BoardDTO> dtoList = result
                .get()
                .map(arr -> entityToDTO((Board) arr[0], (Member) arr[1], (Image) arr[2], ((Number) arr[3]).intValue(), ((Number) arr[4]).intValue()))
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        ImageDTO imageDTO = imageService.get(boardDTO.getIno());
        if (!boardDTO.getWriterId().equals(imageDTO.getUno())) {
            throw new CustomServiceException("WRITER_AND_IMAGE_UPLOADER_MISMATCH");
        }

        Image image = imageService.dtoToEntity(imageDTO);
        if (boardRepository.existsByImage(image)) {
            throw new CustomServiceException("IMAGE_ALREADY_ASSOCIATED");
        }
        Board board = dtoToEntity(boardDTO);
        Board result = boardRepository.save(board);
        return result.getBno();
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow(() -> new CustomServiceException("NOT_EXIST_BOARD"));
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        if (!boardRepository.existsById(bno)) {
            throw new CustomServiceException("NOT_EXIST_BOARD");
        }
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
