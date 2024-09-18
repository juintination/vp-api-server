package com.example.vpapi.repository.search;

import com.example.vpapi.domain.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.example.vpapi.dto.PageRequestDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    private JPQLQuery<Tuple> getBaseQuery() {
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QImage image = QImage.image;
        QReply reply = QReply.reply;
        QHeart heart = QHeart.heart;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.writer.eq(member));
        query.leftJoin(image).on(image.eq(board.image));
        query.leftJoin(reply).on(reply.board.eq(board));
        query.leftJoin(heart).on(heart.board.eq(board));

        return query.select(board, member, image, reply.count(), heart.count()).groupBy(board);
    }

    @Override
    public Page<Object[]> getPagedBoards(PageRequestDTO pageRequestDTO) {

        log.info("getPagedBoards............");

        JPQLQuery<Tuple> tuple = getBaseQuery();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        log.info("result: " + result);

        long count = tuple.fetchCount();
        log.info("count: " + count);

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count
        );

    }

    @Override
    public  Page<Object[]> getPagedBoardsByWriterId(PageRequestDTO pageRequestDTO, Long writerId) {

        log.info("getPagedBoardsByWriterId............");

        JPQLQuery<Tuple> tuple = getBaseQuery();
        tuple.where(QMember.member.mno.eq(writerId));

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count
        );
    }

}
