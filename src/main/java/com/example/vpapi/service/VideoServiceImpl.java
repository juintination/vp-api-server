package com.example.vpapi.service;

import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.Video;
import com.example.vpapi.dto.VideoDTO;
import com.example.vpapi.repository.VideoRepository;
import com.example.vpapi.util.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private final MemberService memberService;

    @Override
    public VideoDTO get(Long vno) {
        Object result = videoRepository.getVideoByVno(vno);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_VIDEO");
        }
        Object[] arr = (Object[]) result;
        return entityToDTO((Video) arr[0], (Member) arr[1]);
    }

    @Override
    public Long register(VideoDTO videoDTO) {
        Video video = dtoToEntity(videoDTO);
        Video result = videoRepository.save(video);
        return result.getVno();
    }

    @Override
    public void remove(Long vno) {
        if (!videoRepository.existsById(vno)) {
            throw new CustomServiceException("NOT_EXIST_VIDEO");
        }
        videoRepository.deleteById(vno);
    }

    @Override
    public Video dtoToEntity(VideoDTO videoDTO) {

        if (videoDTO.getFileName() == null) {
            throw new NullPointerException();
        }

        Member uploader = memberService.dtoToEntity(memberService.get(videoDTO.getUno()));

        return Video.builder()
                .vno(videoDTO.getVno())
                .fileName(videoDTO.getFileName())
                .uploader(uploader)
                .build();
    }

}
