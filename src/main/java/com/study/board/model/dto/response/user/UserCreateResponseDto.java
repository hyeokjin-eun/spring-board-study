package com.study.board.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateResponseDto {

    private Long seq;

    private String id;

    private String email;

    private LocalDateTime created;
}
