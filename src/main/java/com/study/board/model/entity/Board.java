package com.study.board.model.entity;

import com.study.board.model.enumClass.BoardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Accessors(chain = true)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long seq;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '제목'", nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '내용'", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50) COMMENT '상태'", nullable = false)
    private BoardStatus status;

    @CreatedDate
    @Column(columnDefinition = "DATETIME COMMENT '생성 일자'", nullable = false)
    private LocalDateTime created;
}
