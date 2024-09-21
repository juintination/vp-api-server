package com.example.vpapi.service;

import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.Video;
import com.example.vpapi.dto.VideoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VideoService {

    VideoDTO get(Long vno);

    Long register(VideoDTO videoDTO);

    void remove(Long vno);

    Video dtoToEntity(VideoDTO videoDTO);

    default VideoDTO entityToDTO(Video video, Member uploader) {

        if (video.getFileName() == null) {
            throw new NullPointerException();
        }

        return VideoDTO.builder()
                .vno(video.getVno())
                .uno(uploader.getMno())
                .fileName(video.getFileName())
                .regDate(video.getRegDate())
                .modDate(video.getModDate())
                .build();
    }

}
