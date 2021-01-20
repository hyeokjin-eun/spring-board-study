package com.study.board.model.dto.response.board;

import com.study.board.model.enumClass.BoardStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeleteResponseDto {
    private Long seq;

    private String title;

    private String content;

    private BoardStatus status;
}
