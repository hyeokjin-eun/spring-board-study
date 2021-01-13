package com.study.board.service.impl;

import com.study.board.model.dto.response.BoardListResponseDto;
import com.study.board.model.entity.Board;
import com.study.board.model.front.AdminPage;
import com.study.board.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService extends BaseService<Board, BoardListResponseDto> {
    public AdminPage<List<BoardListResponseDto>> list(Pageable pageable) {
        Page<Board> boardList = baseRepository.findAll(pageable);
        List<BoardListResponseDto> boardListResponseDtoList = boardList.stream()
                .map(board -> BoardListResponseDto.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .created(board.getCreated())
                        .build())
                .collect(Collectors.toList());
        return AdminPage.<List<BoardListResponseDto>>builder()
                .url("board/list")
                .data(boardListResponseDtoList)
                .build();
    }
}
