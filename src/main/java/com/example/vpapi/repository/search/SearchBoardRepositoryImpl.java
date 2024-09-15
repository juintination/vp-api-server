package com.example.vpapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.example.vpapi.domain.QBoard;
import com.example.vpapi.domain.Board;
import com.example.vpapi.dto.PageRequestDTO;

import java.util.List;
import java.util.Objects;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> getPagedBoards(PageRequestDTO pageRequestDTO) {

        log.info("searchByPage............");

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

}
