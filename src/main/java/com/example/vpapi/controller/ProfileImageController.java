package com.example.vpapi.controller;

import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.service.adapter.UnifiedImageServiceAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/profile/images")
public class ProfileImageController {

    private final UnifiedImageServiceAdapter unifiedImageServiceAdapter;

    public ProfileImageController(@Qualifier("profileImageServiceAdapter") UnifiedImageServiceAdapter unifiedImageServiceAdapter) {
        this.unifiedImageServiceAdapter = unifiedImageServiceAdapter;
    }

    @GetMapping("/{pino}")
    public ProfileImageDTO get(@PathVariable("pino") Long pino) {
        return unifiedImageServiceAdapter.getProfileImage(pino);
    }

    @GetMapping("/exists/member/{mno}")
    public Boolean existsByMno(@PathVariable("mno") Long mno) {
        return unifiedImageServiceAdapter.existsProfileImageByMno(mno);
    }

    @GetMapping("/member/{mno}")
    public ProfileImageDTO getByMno(@PathVariable("mno") Long mno) {
        return unifiedImageServiceAdapter.getProfileImageByMno(mno);
    }

    @GetMapping("/view/{pino}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("pino") Long pino) {
        return unifiedImageServiceAdapter.viewProfileImage(pino);
    }

    @GetMapping("/view/thumbnail/{pino}")
    public ResponseEntity<Resource> viewThumbnailGET(@PathVariable("pino") Long pino) {
        return unifiedImageServiceAdapter.viewProfileImageThumbnail(pino);
    }

    @GetMapping("/view/member/{mno}")
    public ResponseEntity<Resource> viewFileGetByMno(@PathVariable("mno") Long mno) {
        return unifiedImageServiceAdapter.viewProfileImageByMno(mno);
    }

    @GetMapping("/view/thumbnail/member/{mno}")
    public ResponseEntity<Resource> viewThumbnailGetByMno(@PathVariable("mno") Long mno) {
        return unifiedImageServiceAdapter.viewProfileImageThumbnailByMno(mno);
    }

    @PostMapping("/")
    @PreAuthorize("#profileImageDTO.mno == authentication.principal.mno")
    public Map<String, Long> modifyProfileImage(ProfileImageDTO profileImageDTO) {
        return unifiedImageServiceAdapter.registerProfileImage(profileImageDTO);
    }

    @DeleteMapping("/{pino}")
    public Map<String, String> remove(@PathVariable("pino") Long pino) {
        unifiedImageServiceAdapter.removeProfileImage(pino);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/member/{mno}")
    @PreAuthorize("#mno == authentication.principal.mno")
    public Map<String, String> removeByMno(@PathVariable("mno") Long mno) {
        unifiedImageServiceAdapter.removeProfileImageByMno(mno);
        return Map.of("RESULT", "SUCCESS");
    }

}
