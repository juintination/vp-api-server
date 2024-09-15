package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Heart;
import com.example.vpapi.domain.Member;
import com.example.vpapi.domain.Reply;
import com.example.vpapi.dto.ReplyDTO;
import com.example.vpapi.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final BoardService boardService;

    private final MemberService memberService;

    @Override
    public ReplyDTO get(Long rno) {
        Optional<Reply> result = replyRepository.findById(rno);
        Reply reply = result.orElseThrow();
        return entityToDTO(reply);
    }

    @Override
    public List<Reply> getRepliesByBoard(Long bno) {
        return replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
    }

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        Reply result = replyRepository.save(reply);
        return result.getRno();
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());
        Reply reply = result.orElseThrow();
        reply.changeContent(replyDTO.getContent());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public Reply dtoToEntity(ReplyDTO replyDTO) {
        Member replier = memberService.dtoToEntity(memberService.get(replyDTO.getReplierId()));
        Board board = boardService.dtoToEntity(boardService.get(replyDTO.getBno()));
        return Reply.builder()
                .rno(replyDTO.getRno())
                .content(replyDTO.getContent())
                .board(board)
                .replier(replier)
                .build();
    }

}
