package com.example.vpapi.service;

import com.example.vpapi.domain.Image;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.repository.ImageRepository;
import com.example.vpapi.util.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final MemberService memberService;

    @Override
    public ImageDTO get(Long ino) {
        Object result = imageRepository.getImageByIno(ino);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_IMAGE");
        }
        Object[] arr = (Object[]) result;
        return entityToDTO((Image) arr[0], (Member) arr[1]);
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

    @Override
    public Image dtoToEntity(ImageDTO imageDTO) {

        if (imageDTO.getFileName() == null) {
            throw new NullPointerException();
        }

        Member uploader = memberService.dtoToEntity(memberService.get(imageDTO.getUno()));

        return Image.builder()
                .ino(imageDTO.getIno())
                .fileName(imageDTO.getFileName())
                .uploader(uploader)
                .build();
    }

}
