package com.example.vpapi.repository;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    List<Heart> getHeartsByBoardOrderByHno(Board board);

    Optional<Heart> findByBoardBnoAndMemberMno(Long bno, Long mno);

}
