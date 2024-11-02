package com.example.vpapi.service.adapter;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.ProfileImageDTO;
import com.example.vpapi.service.ImageService;
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
public class ImageServiceAdapter implements UnifiedImageServiceAdapter {

    private final ImageService imageService;

    private final CustomFileUtil fileUtil;

    @Override
    public ImageDTO getImage(Long ino) {
        return imageService.get(ino);
    }

    @Override
    public ProfileImageDTO getProfileImage(Long pino) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public Boolean existsProfileImageByMno(Long mno) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public ProfileImageDTO getProfileImageByMno(Long mno) {
        throw new UnsupportedOperationException("Not supported by ImageService");
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
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageThumbnail(Long pino) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageByMno(Long mno) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public ResponseEntity<Resource> viewProfileImageThumbnailByMno(Long mno) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public Map<String, Long> registerImage(ImageDTO imageDTO) throws IOException {
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
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public void removeImage(Long ino) {
        String oldFileName = imageService.get(ino).getFileName();
        imageService.remove(ino);
        fileUtil.deleteFile(oldFileName);
    }

    @Override
    public void removeProfileImage(Long pino) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    @Override
    public void removeProfileImageByMno(Long mno) {
        throw new UnsupportedOperationException("Not supported by ImageService");
    }

    private String saveFileAndGetFileName(ImageDTO imageDTO) {
        MultipartFile file = imageDTO.getFile();
        return fileUtil.saveFile(file, ImageType.IMAGE);
    }

}
