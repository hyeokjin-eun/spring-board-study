package com.study.board.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardStatus {
    REGISTERED(0, "활성", "게시글 활성 상태"),
    UNREGISTERED(1, "비활성", "게시글 비활성 상태");

    private Integer id;

    private String title;

    private String description;
}
