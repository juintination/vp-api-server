package com.example.vpapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.MemberDTO;
import com.example.vpapi.repository.MemberRepository;
import com.example.vpapi.util.MemberServiceException;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO get(Long mno) {
        return memberRepository.findById(mno)
                .map(this::entityToDTO)
                .orElseThrow(() -> new MemberServiceException("NOT_EXIST_MEMBER"));
    }

    @Override
    public Long getMno(String email) {
        Optional<Member> result = Optional.ofNullable(memberRepository.findByEmail(email));
        Member member = result.orElseThrow(() -> new MemberServiceException("NO_EMAIL_EXISTS"));
        return member.getMno();
    }

    @Override
    public Long register(MemberDTO memberDTO) throws MemberServiceException {

        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new MemberServiceException("EMAIL_ALREADY_EXISTS");
        }

        Member member = memberRepository.save(dtoToEntity(memberDTO));
        return member.getMno();
    }

    @Override
    public void modify(MemberDTO memberDTO) throws MemberServiceException {
        Optional<Member> result = memberRepository.findById(memberDTO.getMno());
        Member member = result.orElseThrow();

        log.info("memberDTO: " + memberDTO);

        if (memberDTO.getEmail() != null && !memberDTO.getEmail().isEmpty()) {
            if (!member.getEmail().equals(memberDTO.getEmail()) && memberRepository.existsByEmail(memberDTO.getEmail())) {
                throw new MemberServiceException("EMAIL_ALREADY_EXISTS");
            }
            try {
                member.changeEmail(memberDTO.getEmail());
            } catch (IllegalArgumentException e) {
                throw new MemberServiceException("INVALID_EMAIL");
            }
        }

        if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
            member.changePassword(passwordEncoder.encode(memberDTO.getPassword()));
        }

        if (memberDTO.getNickname() != null && !memberDTO.getNickname().isEmpty()) {
            member.changeNickname(memberDTO.getNickname());
        }

        memberRepository.save(member);
    }

    @Override
    public void remove(Long mno) {

        if (!memberRepository.existsById(mno)) {
            throw new MemberServiceException("NOT_EXIST_MEMBER");
        }

        memberRepository.deleteById(mno);
    }

    @Override
    public void checkPassword(Long mno, String password) {
        Member member = memberRepository.findById(mno).orElseThrow(() -> new MemberServiceException("NOT_EXIST_MEMBER"));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MemberServiceException("INVALID_PASSWORD");
        }
    }

    @Override
    public Member dtoToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickname(memberDTO.getNickname())
                .memberRole(memberDTO.getRole())
                .build();
    }

}
