package com.study.board.model.dto.response.board;

import com.study.board.model.enumClass.BoardStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCreateResponseDto {

    private Long seq;

    private String title;

    private String content;

    private BoardStatus status;

    private LocalDateTime created;
}
