package com.example.vpapi.service;

import com.example.vpapi.domain.Heart;
import com.example.vpapi.dto.HeartDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface HeartService {

    HeartDTO get(Long hno);

    List<Heart> getHeartsByBoard(Long bno);

    Long register(HeartDTO heartDTO);

    void remove(Long hno);

    Heart dtoToEntity(HeartDTO heartDTO);

    default HeartDTO entityToDTO(Heart heart) {
        return HeartDTO.builder()
                .hno(heart.getHno())
                .bno(heart.getBoard().getBno())
                .memberId(heart.getMember().getMno())
                .memberNickname(heart.getMember().getNickname())
                .memberEmail(heart.getMember().getEmail())
                .regDate(heart.getRegDate())
                .modDate(heart.getModDate())
                .build();
    }

}
