package com.study.board.model.entity;

import com.study.board.model.enumClass.UserRole;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long seq;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '아이디'", nullable = false)
    private String id;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '사용자 성명'", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '비밀 번호'", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '이메일'", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(100) COMMENT '권한'", nullable = false)
    private UserRole role;

    @Column(columnDefinition = "DATETIME COMMENT '생성 일자'", nullable = false)
    private LocalDateTime created;

    @Column(columnDefinition = "DATETIME COMMENT '수정 일자'", nullable = false)
    private LocalDateTime updated;
}
