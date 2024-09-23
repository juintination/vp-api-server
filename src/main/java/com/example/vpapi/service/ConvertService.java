package com.example.vpapi.service;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.VideoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConvertService {

    ImageDTO uploadAndConvertFile(VideoDTO videoDTO);

}
