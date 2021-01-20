package com.study.board.model.dto.response.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListResponseDto {
    private Long seq;

    private String title;

    private String content;

    private LocalDateTime created;
}
