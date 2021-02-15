package com.study.board.model.dto.request.user;

import com.study.board.model.enumClass.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateRequestDto {

    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private UserRole role;
}
