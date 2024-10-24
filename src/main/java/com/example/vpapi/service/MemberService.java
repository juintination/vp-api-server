package com.example.vpapi.service;

import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.ProfileImage;
import com.example.vpapi.dto.MemberDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    MemberDTO get(Long mno);

    Long getMno(String email);

    Long register(MemberDTO memberDTO);

    void modify(MemberDTO modifyDTO);

    void remove(Long mno);

    void checkPassword(Long mno, String password);

    Member dtoToEntity(MemberDTO memberDTO);

    default MemberDTO entityToDTO(Member member, ProfileImage profileImage) {
        Long pino = profileImage != null ? profileImage.getPino() : null;
        return MemberDTO.builder()
                .mno(member.getMno())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .pino(pino)
                .role(member.getMemberRole())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();
    }

}
