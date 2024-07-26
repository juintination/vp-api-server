package com.example.vpapi.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public class CustomMultipartFile implements MultipartFile {

    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final byte[] content;

    public CustomMultipartFile(ResponseEntity<Resource> responseEntity) throws IOException {
        Resource resource = responseEntity.getBody();
        this.name = "file";
        this.originalFilename = resource.getFilename();
        this.contentType = responseEntity.getHeaders().getContentType().toString();
        this.content = resource.getInputStream().readAllBytes();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return this.content.length == 0;
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new java.io.ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
        try (java.io.OutputStream out = new java.io.FileOutputStream(dest)) {
            out.write(this.content);
        }
    }
}
