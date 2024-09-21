package com.example.vpapi.repository;

import com.example.vpapi.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i, u FROM Image i LEFT JOIN i.uploader u WHERE i.ino = :ino")
    Object getImageByIno(@Param("ino") Long ino);

}
