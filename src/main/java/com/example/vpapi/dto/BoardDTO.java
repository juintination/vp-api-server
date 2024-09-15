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
public class BoardDTO {

    private Long bno, ino, writerId;

    private String title, content;

    private String writerNickname, writerEmail;

    private int replyCount, heartCount;

    private LocalDateTime regDate, modDate;

}
