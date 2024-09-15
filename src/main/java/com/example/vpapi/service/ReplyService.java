package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Heart;
import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.Reply;
import com.example.vpapi.dto.ReplyDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ReplyService {

    ReplyDTO get(Long rno);

    List<Reply> getRepliesByBoard(Long bno);

    Long register(ReplyDTO replyDTO);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    Reply dtoToEntity(ReplyDTO replyDTO);

    default ReplyDTO entityToDTO(Reply reply) {
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .bno(reply.getBoard().getBno())
                .content(reply.getContent())
                .replierId(reply.getReplier().getMno())
                .replierNickname(reply.getReplier().getNickname())
                .replierEmail(reply.getReplier().getEmail())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }

}
