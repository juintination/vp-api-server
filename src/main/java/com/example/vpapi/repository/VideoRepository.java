package com.example.vpapi.repository;

import com.example.vpapi.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v, u FROM Video v LEFT JOIN v.uploader u WHERE v.vno = :vno")
    Object getVideoByVno(@Param("vno") Long vno);

}
