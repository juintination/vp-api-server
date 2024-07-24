package com.example.vpapi.service;

import com.example.vpapi.domain.Video;
import com.example.vpapi.dto.VideoDTO;
import com.example.vpapi.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    public VideoDTO get(Long vno) {
        Optional<Video> result = videoRepository.findById(vno);
        Video video = result.orElseThrow();
        return entityToDTO(video);
    }

    @Override
    public Long register(VideoDTO videoDTO) {
        Video video = dtoToEntity(videoDTO);
        Video result = videoRepository.save(video);
        return result.getVno();
    }

    @Override
    public void remove(Long vno) {
        videoRepository.deleteById(vno);
    }

}
