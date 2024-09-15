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
public class HeartDTO {

    private Long hno, bno, memberId, writerId, ino;

    private String memberNickname, memberEmail;

    private LocalDateTime regDate, modDate;

}
