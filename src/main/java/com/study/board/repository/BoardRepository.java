package com.study.board.repository;

import com.study.board.model.entity.Board;
import com.study.board.model.enumClass.BoardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByStatus(Pageable pageable, BoardStatus status);
}
