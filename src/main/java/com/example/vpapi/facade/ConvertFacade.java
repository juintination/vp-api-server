package com.example.vpapi.facade;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.VideoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public interface ConvertFacade {

    ImageDTO uploadAndConvertFile(VideoDTO videoDTO);

    Map<String, Long> convertAndRegister(Long vno);

}
