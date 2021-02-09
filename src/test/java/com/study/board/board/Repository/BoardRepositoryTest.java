package com.study.board.board.Repository;

import com.study.board.model.entity.Board;
import com.study.board.model.enumClass.BoardStatus;
import com.study.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 3; i++) {
            boardRepository.save(Board.builder()
                    .title("BOARD REGISTERED TITLE " + i)
                    .content("BOARD REGISTERED CONTENT " + i)
                    .status(BoardStatus.REGISTERED)
                    .created(LocalDateTime.now())
                    .build());
        }

        for (int i = 0; i < 3; i++) {
            boardRepository.save(Board.builder()
                    .title("BOARD UNREGISTERED TITLE " + i)
                    .content("BOARD UNREGISTERED CONTENT " + i)
                    .status(BoardStatus.UNREGISTERED)
                    .created(LocalDateTime.now())
                    .build());
        }
    }

    @Test
    public void paging_list_by_status_registered() {
        boardRepository.findAllByStatus(Pageable.unpaged(), BoardStatus.REGISTERED)
                .forEach(System.out::println);
    }
}
