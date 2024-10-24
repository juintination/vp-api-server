package com.example.vpapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.vpapi.domain.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query("SELECT p, m " +
            " FROM ProfileImage p LEFT JOIN p.member m " +
            " WHERE m.mno = :mno")
    Object getProfileImageByMno(@Param("mno") Long mno);

    @Query("SELECT p FROM ProfileImage p WHERE p.member.mno = :mno")
    ProfileImage findByMno(@Param("mno") Long mno);

    @Query("SELECT COUNT(p) > 0 FROM ProfileImage p WHERE p.member.mno = :mno")
    boolean existsByMno(@Param("mno") Long mno);

    @Query("SELECT p, m " +
            " FROM ProfileImage p LEFT JOIN p.member m " +
            " WHERE p.pino = :pino")
    Object getProfileImageByPino(@Param("pino") Long pino);

}
