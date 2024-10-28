package com.example.vpapi.util;

import com.example.vpapi.util.strategy.ImageThumbnailStrategy;
import com.example.vpapi.util.strategy.ProfileImageThumbnailStrategy;
import com.example.vpapi.util.strategy.ThumbnailStrategy;
import lombok.Getter;

@Getter
public enum ImageType {

    IMAGE(new ImageThumbnailStrategy()),
    PROFILE_IMAGE(new ProfileImageThumbnailStrategy());

    private final ThumbnailStrategy thumbnailStrategy;

    ImageType(ThumbnailStrategy thumbnailStrategy) {
        this.thumbnailStrategy = thumbnailStrategy;
    }

}
