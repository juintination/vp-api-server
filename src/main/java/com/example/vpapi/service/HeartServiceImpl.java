package com.example.vpapi.service;

import com.example.vpapi.domain.Board;
import com.example.vpapi.domain.Heart;
import com.example.vpapi.domain.Member;
import com.example.vpapi.dto.HeartDTO;
import com.example.vpapi.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;

    private final BoardService boardService;

    private final MemberService memberService;

    @Override
    public HeartDTO get(Long hno) {
        Optional<Heart> result = heartRepository.findById(hno);
        Heart heart = result.orElseThrow();
        return entityToDTO(heart);
    }

    @Override
    public List<HeartDTO> getHeartsByBoard(Long bno) {
        List<Heart> result = heartRepository.getHeartsByBoardOrderByHno(Board.builder().bno(bno).build());
        return result.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public Long register(HeartDTO heartDTO) {
        Heart heart = dtoToEntity(heartDTO);
        Heart result = heartRepository.save(heart);
        return result.getHno();
    }

    @Override
    public void remove(Long hno) {
        heartRepository.deleteById(hno);
    }

    @Override
    public Heart dtoToEntity(HeartDTO heartDTO) {
        Member member = memberService.dtoToEntity(memberService.get(heartDTO.getMemberId()));
        Board board = boardService.dtoToEntity(boardService.get(heartDTO.getBno()));
        return Heart.builder()
                .hno(heartDTO.getHno())
                .member(member)
                .board(board)
                .build();
    }

}
