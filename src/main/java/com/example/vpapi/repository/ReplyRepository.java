package com.example.vpapi.repository;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> getRepliesByBoardOrderByRno(Board board);

}
