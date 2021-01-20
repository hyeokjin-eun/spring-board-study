package com.study.board.model.dto.request.board;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequestDto {

    private String title;

    private String content;
}
