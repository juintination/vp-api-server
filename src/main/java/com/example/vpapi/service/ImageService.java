package com.example.vpapi.service;

import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.ImageDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ImageService {

    ImageDTO get(Long vno);

    Long register(ImageDTO imageDTO);

    void remove(Long vno);

    Image dtoToEntity(ImageDTO imageDTO);

    default ImageDTO entityToDTO(Image image, Member uploader) {

        if (image.getFileName() == null) {
            throw new NullPointerException();
        }

        return ImageDTO.builder()
                .ino(image.getIno())
                .uno(uploader.getMno())
                .regDate(image.getRegDate())
                .modDate(image.getModDate())
                .fileName(image.getFileName())
                .build();
    }

}
