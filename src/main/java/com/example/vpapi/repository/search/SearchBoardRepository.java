package com.example.vpapi.repository.search;

import org.springframework.data.domain.Page;
import com.example.vpapi.dto.PageRequestDTO;

public interface SearchBoardRepository {

    Page<Object[]> getPagedBoards(PageRequestDTO pageRequestDTO);

    Page<Object[]> getPagedBoardsByWriterId(PageRequestDTO pageRequestDTO, Long writerId);
    
}
