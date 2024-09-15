package com.example.vpapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno, bno, replierId;

    private String content;

    private String replierNickname, replierEmail;

    private LocalDateTime regDate, modDate;

}
