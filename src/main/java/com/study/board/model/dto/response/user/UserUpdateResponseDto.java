package com.study.board.model.dto.response.user;

import com.study.board.model.enumClass.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponseDto {

    private Long seq;

    private String id;

    private String password;

    private String email;

    private UserRole role;

    private LocalDateTime created;

    private LocalDateTime updated;
}
