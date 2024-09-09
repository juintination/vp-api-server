package com.example.vpapi.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.vpapi.domain.Member;
import com.example.vpapi.repository.MemberRepository;
import com.example.vpapi.security.dto.CustomUserDetails;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("----------------------loadUserByUsername----------------------");

        Member member = memberRepository.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        log.info("loadUserByUsername: " + member);
        return new CustomUserDetails(member);

    }

}
