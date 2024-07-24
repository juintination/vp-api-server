package com.example.vpapi.service;

import com.example.vpapi.domain.Video;
import com.example.vpapi.dto.VideoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VideoService {

    VideoDTO get(Long vno);

    Long register(VideoDTO videoDTO);

    void remove(Long vno);

    default Video dtoToEntity(VideoDTO videoDTO) {

        if (videoDTO.getFileName() == null) {
            throw new NullPointerException();
        }

        return Video.builder()
                .vno(videoDTO.getVno())
                .fileName(videoDTO.getFileName())
                .build();
    }

    default VideoDTO entityToDTO(Video video) {

        if (video.getFileName() == null) {
            throw new NullPointerException();
        }

        return VideoDTO.builder()
                .vno(video.getVno())
                .fileName(video.getFileName())
                .build();
    }

}
