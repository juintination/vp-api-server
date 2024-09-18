package com.example.vpapi.service;

import com.example.vpapi.domain.Image;
import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.repository.ImageRepository;
import com.example.vpapi.util.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ImageDTO get(Long ino) {
        Optional<Image> result = imageRepository.findById(ino);
        Image image = result.orElseThrow(() -> new CustomServiceException("NOT_EXIST_IMAGE"));
        return entityToDTO(image);
    }

    @Override
    public Long register(ImageDTO imageDTO) {
        Image image = dtoToEntity(imageDTO);
        Image result = imageRepository.save(image);
        return result.getIno();
    }

    @Override
    public void remove(Long ino) {
        if (!imageRepository.existsById(ino)) {
            throw new CustomServiceException("NOT_EXIST_IMAGE");
        }
        imageRepository.deleteById(ino);
    }

}
