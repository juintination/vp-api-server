package com.example.vpapi.controller;

import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.facade.ImageFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/profile/images")
public class ProfileImageController {

    private final ImageFacade imageFacade;

    @GetMapping("/{pino}")
    public ProfileImageDTO get(@PathVariable("pino") Long pino) {
        return imageFacade.getProfileImage(pino);
    }

    @GetMapping("/exists/member/{mno}")
    public Map<String, Boolean> existsByMno(@PathVariable("mno") Long mno) {
        return Map.of("RESULT", imageFacade.existsProfileImageByMno(mno));
    }

    @GetMapping("/member/{mno}")
    public ProfileImageDTO getByMno(@PathVariable("mno") Long mno) {
        return imageFacade.getProfileImageByMno(mno);
    }

    @GetMapping("/view/{pino}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("pino") Long pino) {
        return imageFacade.viewProfileImage(pino);
    }

    @GetMapping("/view/thumbnail/{pino}")
    public ResponseEntity<Resource> viewThumbnailGET(@PathVariable("pino") Long pino) {
        return imageFacade.viewProfileImageThumbnail(pino);
    }

    @GetMapping("/view/member/{mno}")
    public ResponseEntity<Resource> viewFileGetByMno(@PathVariable("mno") Long mno) {
        return imageFacade.viewProfileImageByMno(mno);
    }

    @GetMapping("/view/thumbnail/member/{mno}")
    public ResponseEntity<Resource> viewThumbnailGetByMno(@PathVariable("mno") Long mno) {
        return imageFacade.viewProfileImageThumbnailByMno(mno);
    }

    @PostMapping("/")
    @PreAuthorize("#profileImageDTO.mno == authentication.principal.mno")
    public Map<String, Long> modifyProfileImage(ProfileImageDTO profileImageDTO) {
        return imageFacade.modifyProfileImage(profileImageDTO);
    }

    @DeleteMapping("/{pino}")
    public Map<String, String> remove(@PathVariable(name="pino") Long pino) {
        imageFacade.removeProfileImage(pino);
        return Map.of("RESULT", "SUCCESS");
    }

}
