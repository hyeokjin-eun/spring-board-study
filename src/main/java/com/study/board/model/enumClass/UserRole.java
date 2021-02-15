package com.study.board.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  UserRole {
    USER("USER", "사용자 권한");

    private final String code;

    private final String description;
}
