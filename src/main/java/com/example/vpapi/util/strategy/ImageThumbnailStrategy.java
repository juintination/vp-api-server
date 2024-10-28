package com.example.vpapi.util.strategy;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.io.IOException;
import java.nio.file.Path;

public class ImageThumbnailStrategy implements ThumbnailStrategy {

    @Override
    public void createThumbnail(Path sourcePath, Path thumbnailPath) throws IOException {
        Thumbnails.of(sourcePath.toFile())
                .sourceRegion(Positions.CENTER, 800, 800)
                .size(800, 800)
                .toFile(thumbnailPath.toFile());
    }

}
