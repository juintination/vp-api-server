package com.example.vpapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConvertServiceImpl implements ConvertService {

    @Value("${convert.server.url}")
    private String serverUrl;

    @Override
    public ResponseEntity<Resource> uploadAndConvertFile(Resource fileResource) {
        try {

            if (fileResource == null) {
                return ResponseEntity.badRequest().build();
            }

            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            return restTemplate.exchange(
                    serverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Resource.class
            );
        } catch (Exception e) {
            log.error("Upload failed: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
