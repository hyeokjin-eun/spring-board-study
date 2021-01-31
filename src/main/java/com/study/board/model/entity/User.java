package com.study.board.model.entity;

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

    @Column(columnDefinition = "VARCHAR(100) COMMENT '비밀번호'", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '이메일'", nullable = false)
    private String email;

    @Column(columnDefinition = "DATETIME COMMENT '생성 일자'", nullable = false)
    private LocalDateTime created;

    @Column(columnDefinition = "DATETIME COMMENT '수정 일자'", nullable = false)
    private LocalDateTime updated;
}
