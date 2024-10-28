package com.example.vpapi.facade;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.service.ImageService;
import com.example.vpapi.service.ProfileImageService;
import com.example.vpapi.util.CustomFileUtil;
import com.example.vpapi.util.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageFacadeImpl implements ImageFacade {

    private final ImageService imageService;

    private final ProfileImageService profileImageService;

    private final CustomFileUtil fileUtil;

    @Override
    public ImageDTO getImage(Long ino) {
        return imageService.get(ino);
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
    public Map<String, String> viewImage(Long ino) throws IOException {
        String fileName = getImage(ino).getFileName();
        Resource fileResource = fileUtil.getFile(fileName).getBody();
        assert fileResource != null;
        byte[] fileContent = fileUtil.getFileContent(fileResource);
        String base64FileContent = Base64.getEncoder().encodeToString(fileContent);
        return Map.of("fileContent", base64FileContent);
    }

    @Override
    public Map<String, String> viewImageThumbnail(Long ino) throws IOException {
        String fileName = "s_" + getImage(ino).getFileName();
        Resource fileResource = fileUtil.getFile(fileName).getBody();
        assert fileResource != null;
        byte[] fileContent = fileUtil.getFileContent(fileResource);
        String base64FileContent = Base64.getEncoder().encodeToString(fileContent);
        return Map.of("fileContent", base64FileContent);
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
        String fileName = getProfileImageByMno(mno).getFileName();
        return fileUtil.getFile(fileName);
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageThumbnailByMno(Long mno) {
        String fileName = "s_" + getProfileImageByMno(mno).getFileName();
        return fileUtil.getFile(fileName);
    }

    @Override
    public Map<String, Long> registerImage(ImageDTO imageDTO) {
        imageDTO.setFileName(saveFileAndGetFileName(imageDTO));
        Long ino = imageService.register(imageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("ino", ino);
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
        String oldFileName = imageService.get(ino).getFileName();
        imageService.remove(ino);
        fileUtil.deleteFile(oldFileName);
    }

    @Override
    public void removeProfileImage(Long pino) {
        String oldFileName = profileImageService.get(pino).getFileName();
        profileImageService.remove(pino);
        fileUtil.deleteFile(oldFileName);
    }

    private String saveFileAndGetFileName(ImageDTO imageDTO) {
        MultipartFile file = imageDTO.getFile();
        return fileUtil.saveFile(file, ImageType.IMAGE);
    }

    private String saveFileAndGetFileName(ProfileImageDTO profileImageDTO) {
        MultipartFile file = profileImageDTO.getFile();
        return fileUtil.saveFile(file, ImageType.PROFILE_IMAGE);
    }

}
