package com.study.board.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponseDto {

    private Long seq;

    private String id;

    private String password;

    private String email;

    private LocalDateTime created;

    private LocalDateTime updated;
}
