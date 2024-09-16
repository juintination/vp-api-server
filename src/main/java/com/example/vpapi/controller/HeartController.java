package com.example.vpapi.controller;

import com.example.vpapi.dto.HeartDTO;
import com.example.vpapi.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/hearts")
public class HeartController {

    private final HeartService heartService;

    @GetMapping("/{hno}")
    public HeartDTO get(@PathVariable("hno") Long hno) {
        return heartService.get(hno);
    }

    @GetMapping("/board/{bno}")
    public List<HeartDTO> getHeartsByBoard(@PathVariable("bno") Long bno) {
        return heartService.getHeartsByBoard(bno);
    }

    @PostMapping("/")
    public Map<String, Long> register(HeartDTO dto) throws Exception {
        long hno = heartService.register(dto);
        return Map.of("HNO", hno);
    }

    @DeleteMapping("/{hno}")
    public Map<String, String> remove(@PathVariable("hno") Long hno) {
        heartService.remove(hno);
        return Map.of("RESULT", "SUCCESS");
    }

}
