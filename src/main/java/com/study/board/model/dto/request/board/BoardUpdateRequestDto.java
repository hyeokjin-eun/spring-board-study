package com.study.board.model.dto.request.board;

import com.study.board.model.enumClass.BoardStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUpdateRequestDto {
    private Long seq;

    private String title;

    private String content;

    private BoardStatus status;
}
