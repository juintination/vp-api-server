package com.example.vpapi.service;

import org.springframework.transaction.annotation.Transactional;
import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.ProfileImage;
import com.example.vpapi.dto.ProfileImageDTO;

@Transactional
public interface ProfileImageService {

    ProfileImageDTO get(Long ino);

    ProfileImageDTO getByMno(Long mno);

    boolean existsByMno(Long mno);

    Long register(ProfileImageDTO profileImageDTO);

    void remove(Long ino);

    void removeByMno(Long mno);

    ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO);

    default ProfileImageDTO entityToDTO(ProfileImage profileImage, Member member) {

        if (profileImage.getFileName() == null) {
            throw new NullPointerException();
        }

        return ProfileImageDTO.builder()
                .pino(profileImage.getPino())
                .fileName(profileImage.getFileName())
                .mno(member.getMno())
                .build();
    }

}
