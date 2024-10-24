package com.example.vpapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.service.ProfileImageService;
import com.example.vpapi.util.CustomFileUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/profile/images")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    private final CustomFileUtil fileUtil;

    @GetMapping("/{pino}")
    public ProfileImageDTO get(@PathVariable("pino") Long pino) {
        return profileImageService.get(pino);
    }

    @GetMapping("/member/{mno}")
    public ProfileImageDTO getByMno(@PathVariable("mno") Long mno) {
        return profileImageService.getByMno(mno);
    }

    @GetMapping("/view/{pino}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("pino") Long pino) {
        String fileName = get(pino).getFileName();
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/view/thumbnail/{pino}")
    public ResponseEntity<Resource> viewThumbnailGET(@PathVariable("pino") Long pino) {
        String fileName = "s_" + get(pino).getFileName();
        return fileUtil.getFile(fileName);
    }

    @PostMapping("/")
    @PreAuthorize("#profileImageDTO.mno == authentication.principal.mno")
    public Map<String, Long> modifyProfileImage(ProfileImageDTO profileImageDTO) {

        if (profileImageService.existsByMno(profileImageDTO.getMno())) {
            ProfileImageDTO existingProfileImage = profileImageService.getByMno(profileImageDTO.getMno());
            fileUtil.deleteFile(existingProfileImage.getFileName());
        }

        MultipartFile file = profileImageDTO.getFile();
        String uploadFileName = fileUtil.saveFile(file);
        profileImageDTO.setFileName(uploadFileName);
        Long pino = profileImageService.register(profileImageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("pino", pino);
    }

    @DeleteMapping("/{pino}")
    public Map<String, String> remove(@PathVariable(name="pino") Long pino) {
        String oldFileName = profileImageService.get(pino).getFileName();
        profileImageService.remove(pino);
        fileUtil.deleteFile(oldFileName);
        return Map.of("RESULT", "SUCCESS");
    }

}
