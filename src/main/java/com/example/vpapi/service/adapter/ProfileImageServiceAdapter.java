package com.example.vpapi.service.adapter;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.service.ProfileImageService;
import com.example.vpapi.util.CustomFileUtil;
import com.example.vpapi.util.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileImageServiceAdapter implements UnifiedImageServiceAdapter {

    private final ProfileImageService profileImageService;

    private final CustomFileUtil fileUtil;

    @Override
    public ImageDTO getImage(Long ino) {
        throw new UnsupportedOperationException("Not supported by ProfileImageService");
    }

    @Override
    public ProfileImageDTO getProfileImage(Long pino) {
        return profileImageService.get(pino);
    }

    @Override
    public Boolean existsProfileImageByMno(Long mno) {
        return profileImageService.existsByMno(mno);
    }

    @Override
    public ProfileImageDTO getProfileImageByMno(Long mno) {
        return profileImageService.getByMno(mno);
    }

    @Override
    public Map<String, String> viewImage(Long ino) {
        throw new UnsupportedOperationException("Not supported by ProfileImageService");
    }

    @Override
    public Map<String, String> viewImageThumbnail(Long ino) {
        throw new UnsupportedOperationException("Not supported by ProfileImageService");
    }

    @Override
    public ResponseEntity<Resource> viewProfileImage(Long pino) {
        String fileName = getProfileImage(pino).getFileName();
        return fileUtil.getFile(fileName);
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageThumbnail(Long pino) {
        String fileName = "s_" + getProfileImage(pino).getFileName();
        return fileUtil.getFile(fileName);
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageByMno(Long mno) {
        String defaultProfileImage = "cabbi.png";
        if (existsProfileImageByMno(mno)) {
            String fileName = getProfileImageByMno(mno).getFileName();
            return fileUtil.getFile(fileName);
        } else {
            return fileUtil.getFile(defaultProfileImage);
        }
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageThumbnailByMno(Long mno) {
        String defaultProfileThumbnail = "s_cabbi.png";
        if (existsProfileImageByMno(mno)) {
            String fileName = "s_" + getProfileImageByMno(mno).getFileName();
            return fileUtil.getFile(fileName);
        } else {
            return fileUtil.getFile(defaultProfileThumbnail);
        }
    }

    @Override
    public Map<String, Long> registerImage(ImageDTO imageDTO) {
        throw new UnsupportedOperationException("Not supported by ProfileImageService");
    }

    @Override
    public Map<String, Long> modifyProfileImage(ProfileImageDTO profileImageDTO) {
        if (profileImageService.existsByMno(profileImageDTO.getMno())) {
            ProfileImageDTO existingProfileImage = profileImageService.getByMno(profileImageDTO.getMno());
            fileUtil.deleteFile(existingProfileImage.getFileName());
        }

        profileImageDTO.setFileName(saveFileAndGetFileName(profileImageDTO));
        Long pino = profileImageService.register(profileImageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("pino", pino);
    }

    @Override
    public void removeImage(Long ino) {
        throw new UnsupportedOperationException("Not supported by ProfileImageService");
    }

    @Override
    public void removeProfileImage(Long pino) {
        String oldFileName = profileImageService.get(pino).getFileName();
        profileImageService.remove(pino);
        fileUtil.deleteFile(oldFileName);
    }

    @Override
    public void removeProfileImageByMno(Long mno) {
        if (profileImageService.existsByMno(mno)) {
            String oldFileName = profileImageService.getByMno(mno).getFileName();
            profileImageService.removeByMno(mno);
            fileUtil.deleteFile(oldFileName);
        }
    }

    private String saveFileAndGetFileName(ProfileImageDTO profileImageDTO) {
        MultipartFile file = profileImageDTO.getFile();
        return fileUtil.saveFile(file, ImageType.PROFILE_IMAGE);
    }

}
