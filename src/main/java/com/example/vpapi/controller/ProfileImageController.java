package com.example.vpapi.controller;

import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.service.UnifiedImageService;
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

    private final UnifiedImageService unifiedImageService;

    public ProfileImageController(@Qualifier("profileImageServiceAdapter") UnifiedImageService unifiedImageService) {
        this.unifiedImageService = unifiedImageService;
    }

    @GetMapping("/{pino}")
    public ProfileImageDTO get(@PathVariable("pino") Long pino) {
        return unifiedImageService.getProfileImage(pino);
    }

    @GetMapping("/exists/member/{mno}")
    public Boolean existsByMno(@PathVariable("mno") Long mno) {
        return unifiedImageService.existsProfileImageByMno(mno);
    }

    @GetMapping("/member/{mno}")
    public ProfileImageDTO getByMno(@PathVariable("mno") Long mno) {
        return unifiedImageService.getProfileImageByMno(mno);
    }

    @GetMapping("/view/{pino}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("pino") Long pino) {
        return unifiedImageService.viewProfileImage(pino);
    }

    @GetMapping("/view/thumbnail/{pino}")
    public ResponseEntity<Resource> viewThumbnailGET(@PathVariable("pino") Long pino) {
        return unifiedImageService.viewProfileImageThumbnail(pino);
    }

    @GetMapping("/view/member/{mno}")
    public ResponseEntity<Resource> viewFileGetByMno(@PathVariable("mno") Long mno) {
        return unifiedImageService.viewProfileImageByMno(mno);
    }

    @GetMapping("/view/thumbnail/member/{mno}")
    public ResponseEntity<Resource> viewThumbnailGetByMno(@PathVariable("mno") Long mno) {
        return unifiedImageService.viewProfileImageThumbnailByMno(mno);
    }

    @PostMapping("/")
    @PreAuthorize("#profileImageDTO.mno == authentication.principal.mno")
    public Map<String, Long> modifyProfileImage(ProfileImageDTO profileImageDTO) {
        return unifiedImageService.modifyProfileImage(profileImageDTO);
    }

    @DeleteMapping("/{pino}")
    public Map<String, String> remove(@PathVariable("pino") Long pino) {
        unifiedImageService.removeProfileImage(pino);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/member/{mno}")
    public Map<String, String> removeByMno(@PathVariable("mno") Long mno) {
        unifiedImageService.removeProfileImageByMno(mno);
        return Map.of("RESULT", "SUCCESS");
    }

}
