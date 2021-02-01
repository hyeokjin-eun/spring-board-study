package com.study.board.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponseDto {

    private Long seq;

    private String id;

    private String email;

    private LocalDateTime created;

    private LocalDateTime updated;
}
