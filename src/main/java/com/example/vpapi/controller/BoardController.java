package com.example.vpapi.controller;

import com.example.vpapi.dto.BoardDTO;
import com.example.vpapi.dto.PageRequestDTO;
import com.example.vpapi.dto.PageResponseDTO;
import com.example.vpapi.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{bno}")
    public BoardDTO get(@PathVariable("bno") Long bno) {
        return boardService.get(bno);
    }

    @GetMapping("/list")
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        return boardService.getList(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(BoardDTO dto) throws Exception {
        long bno = boardService.register(dto);
        return Map.of("BNO", bno);
    }

    @PutMapping("/{bno}")
    public Map<String, String> modify(@PathVariable("bno") Long bno, BoardDTO dto) throws Exception {
        dto.setBno(bno);
        boardService.modify(dto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{bno}")
    public Map<String, String> remove(@PathVariable("bno") Long bno) {
        boardService.remove(bno);
        return Map.of("RESULT", "SUCCESS");
    }

}
