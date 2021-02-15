package com.study.board.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardStatus {
    REGISTERED(0, "활성", "게시글 활성 상태"),
    UNREGISTERED(1, "비활성", "게시글 비활성 상태");

    private final Integer id;

    private final String title;

    private final String description;
}
