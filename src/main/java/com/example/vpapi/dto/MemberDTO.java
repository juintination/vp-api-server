package com.example.vpapi.dto;

import lombok.*;
import com.example.vpapi.domain.MemberRole;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long mno;

    private String email;

    private String password;

    private String nickname;

    private MemberRole role;

    private LocalDateTime regDate;

}
