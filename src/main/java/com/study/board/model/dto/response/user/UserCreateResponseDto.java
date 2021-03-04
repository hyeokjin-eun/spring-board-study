package com.study.board.model.dto.response.user;

import com.study.board.model.enumClass.UserRole;
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

    private UserRole role;

    private String username;

    private LocalDateTime created;
}
