package com.example.vpapi.service;

import com.example.vpapi.domain.Image;
import com.example.vpapi.dto.ImageDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ImageService {

    ImageDTO get(Long vno);

    Long register(ImageDTO imageDTO);

    void remove(Long vno);

    default Image dtoToEntity(ImageDTO imageDTO) {

        if (imageDTO.getFileName() == null) {
            throw new NullPointerException();
        }

        return Image.builder()
                .ino(imageDTO.getIno())
                .fileName(imageDTO.getFileName())
                .build();
    }

    default ImageDTO entityToDTO(Image image) {

        if (image.getFileName() == null) {
            throw new NullPointerException();
        }

        return ImageDTO.builder()
                .ino(image.getIno())
                .fileName(image.getFileName())
                .build();
    }

}
