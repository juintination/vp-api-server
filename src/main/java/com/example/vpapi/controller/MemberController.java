package com.example.vpapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.vpapi.dto.MemberDTO;
import com.example.vpapi.service.MemberService;
import com.example.vpapi.util.CustomJWTException;
import com.example.vpapi.util.JWTUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{mno}")
    public MemberDTO get(@PathVariable("mno") Long mno) {
        return memberService.get(mno);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody MemberDTO dto) throws Exception {
        long mno = memberService.register(dto);
        return Map.of("MNO", mno);
    }

    @PutMapping("/{mno}")
    @PreAuthorize("#mno == authentication.principal.mno")
    public Map<String, String> modify(@PathVariable("mno") Long mno, @RequestBody MemberDTO dto) throws Exception {
        dto.setMno(mno);
        memberService.modify(dto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{mno}")
    @PreAuthorize("#mno == authentication.principal.mno")
    public Map<String, String> remove(@PathVariable("mno") Long mno) {
        memberService.remove(mno);
        return Map.of("RESULT", "SUCCESS");
    }

    @PostMapping("/checkPassword")
    public Map<String, String> checkPassword(@RequestBody MemberDTO dto) {
        memberService.checkPassword(dto.getMno(), dto.getPassword());
        return Map.of("RESULT", "SUCCESS");
    }

    @RequestMapping("/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken) {

        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID_STRING");
        }

        String accessToken = authHeader.substring(7);

        // accessToken이 만료되지 않았다면
        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // refreshToken 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);

        log.info("refresh ... claims: " + claims);

        String newAccessToken = JWTUtil.generateToken(claims, 10);
        String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    private boolean checkTime(Integer exp) {
        java.util.Date expDate = new java.util.Date((long) exp * (1000));
        long gap = expDate.getTime() - System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);
        return leftMin < 60;
    }

    private boolean checkExpiredToken(String token) {
        try {
            JWTUtil.validateToken(token);
        } catch(CustomJWTException e) {
            if (e.getMessage().equals("Expired")) {
                return true;
            }
        }
        return false;
    }

}
