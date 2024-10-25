package com.example.vpapi.facade;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.VideoDTO;
import com.example.vpapi.service.ImageService;
import com.example.vpapi.service.VideoService;
import com.example.vpapi.util.CustomFileUtil;
import com.example.vpapi.util.CustomMultipartFile;
import com.example.vpapi.util.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConvertFacadeImpl implements ConvertFacade {

    @Value("${convert.server.url}")
    private String convertServerUrl;

    private final VideoService videoService;

    private final ImageService imageService;

    private final CustomFileUtil fileUtil;

    @Override
    public ImageDTO uploadAndConvertFile(VideoDTO videoDTO) {
        String fileName = videoDTO.getFileName();
        ResponseEntity<Resource> fileResponse = fileUtil.getFile(fileName);
        Resource fileResource = fileResponse.getBody();
        try {

            if (fileResource == null) {
                throw new CustomServiceException("NO_FILE_PROVIDED");
            }

            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Resource> responseEntity = restTemplate.exchange(
                    convertServerUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Resource.class
            );

            CustomMultipartFile file = new CustomMultipartFile(responseEntity);
            log.info("File uploaded: {}", file.getOriginalFilename());

            String uploadFileName = fileUtil.saveFile(file);
            log.info("File converted: {}", uploadFileName);

            return ImageDTO.builder()
                    .fileName(uploadFileName)
                    .file(file)
                    .uno(videoDTO.getUno())
                    .build();
        } catch (Exception e) {
            log.error("Upload failed: {}", e.getMessage(), e);
            throw new CustomServiceException("FILE_UPLOAD_FAILED");
        }
    }

    @Override
    public Map<String, Long> convertAndRegister(Long vno) {
        VideoDTO videoDTO = videoService.get(vno);
        ImageDTO imageDTO = uploadAndConvertFile(videoDTO);
        Long ino = imageService.register(imageDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("ino", ino);
    }

}
