package com.study.board.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeleteResponseDto {

    private Long seq;

    private String id;

    private String email;

    private LocalDateTime updated;
}
