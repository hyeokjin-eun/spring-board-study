package com.study.board.model.dto.request.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {

    private String id;

    private String password;

    private String email;
}
