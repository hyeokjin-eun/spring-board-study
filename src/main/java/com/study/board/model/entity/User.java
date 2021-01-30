package com.study.board.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long seq;

    private String id;

    private String password;

    private String email;

    private LocalDateTime created;

    private LocalDateTime updated;
}
