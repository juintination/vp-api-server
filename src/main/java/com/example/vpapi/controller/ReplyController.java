package com.example.vpapi.controller;

import com.example.vpapi.dto.ReplyDTO;
import com.example.vpapi.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/replies")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{rno}")
    public ReplyDTO get(@PathVariable("rno") Long rno) {
        return replyService.get(rno);
    }

    @GetMapping("/board/{bno}")
    public List<ReplyDTO> getRepliesByBoard(@PathVariable("bno") Long bno) {
        return replyService.getRepliesByBoard(bno);
    }

    @PostMapping("/")
    public Map<String, Long> register(ReplyDTO dto) throws Exception {
        long rno = replyService.register(dto);
        return Map.of("RNO", rno);
    }

    @PutMapping("/{rno}")
    public Map<String, String> modify(@PathVariable("rno") Long rno, ReplyDTO dto) throws Exception {
        dto.setRno(rno);
        replyService.modify(dto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{rno}")
    public Map<String, String> remove(@PathVariable("rno") Long rno) {
        replyService.remove(rno);
        return Map.of("RESULT", "SUCCESS");
    }

}
