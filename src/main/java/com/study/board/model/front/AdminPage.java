package com.study.board.model.front;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminPage<T> {

    private String url;

    private T data;
}
