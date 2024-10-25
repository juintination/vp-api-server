package com.example.vpapi.controller;

import com.example.vpapi.facade.ConvertFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/convert")
public class ConvertController {

    private final ConvertFacade convertFacade;

    @GetMapping("/{vno}")
    public Map<String, Long> convertFile(@PathVariable Long vno) {
        return convertFacade.convertAndRegister(vno);
    }

}
