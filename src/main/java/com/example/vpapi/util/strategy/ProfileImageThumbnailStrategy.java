package com.example.vpapi.util.strategy;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.nio.file.Path;

public class ProfileImageThumbnailStrategy implements ThumbnailStrategy {

    @Override
    public void createThumbnail(Path sourcePath, Path thumbnailPath) throws IOException {
        Thumbnails.of(sourcePath.toFile())
                .size(200, 200)
                .keepAspectRatio(true)
                .toFile(thumbnailPath.toFile());
    }

}
