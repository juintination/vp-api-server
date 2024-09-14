package com.example.vpapi.repository;

import com.example.vpapi.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b, w, i, count(r), count(h) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT JOIN b.image i " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " LEFT OUTER JOIN Heart h ON h.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
