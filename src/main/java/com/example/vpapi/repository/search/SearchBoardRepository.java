package com.example.vpapi.repository.search;

import com.example.vpapi.domain.Board;
import org.springframework.data.domain.Page;
import com.example.vpapi.dto.PageRequestDTO;

public interface SearchBoardRepository {

    Page<Board> getPagedBoards(PageRequestDTO pageRequestDTO);
    
}
