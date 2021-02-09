package com.study.board.service.impl;

import com.study.board.model.dto.request.board.BoardCreateRequestDto;
import com.study.board.model.dto.request.board.BoardUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.board.*;
import com.study.board.model.entity.Board;
import com.study.board.model.enumClass.BoardStatus;
import com.study.board.model.exception.BoardNotFoundException;
import com.study.board.repository.BoardRepository;
import com.study.board.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService extends BaseService<Board, BoardCreateRequestDto, BoardUpdateRequestDto, BoardListResponseDto, BoardCreateResponseDto, BoardUpdateResponseDto, BoardDeleteResponseDto, BoardDetailResponseDto> {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public ResponseDto<BoardCreateResponseDto> create(final BoardCreateRequestDto boardCreateRequestDto) {
        return Optional.of(
                baseRepository.save(Board.builder()
                        .title(boardCreateRequestDto.getTitle())
                        .content(boardCreateRequestDto.getContent())
                        .status(BoardStatus.REGISTERED)
                        .created(LocalDateTime.now())
                        .build()))
                .map(board -> ResponseDto.OK(BoardCreateResponseDto.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .status(board.getStatus())
                        .created(board.getCreated())
                        .build()))
                .orElseThrow(BoardNotFoundException::new);
    }

    @Override
    public ResponseDto<BoardUpdateResponseDto> update(final BoardUpdateRequestDto boardUpdateRequestDto) {
        return baseRepository.findById(boardUpdateRequestDto.getSeq())
                .map(board -> board
                        .setSeq(boardUpdateRequestDto.getSeq())
                        .setTitle(boardUpdateRequestDto.getTitle())
                        .setContent(boardUpdateRequestDto.getContent())
                        .setStatus(boardUpdateRequestDto.getStatus()))
                .map(board -> baseRepository.save(board))
                .map(board -> ResponseDto.OK(BoardUpdateResponseDto.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .status(board.getStatus())
                        .build()))
                .orElseThrow(BoardNotFoundException::new);
    }

    @Override
    public ResponseDto<BoardDeleteResponseDto> delete(final Long seq) {
        return baseRepository.findById(seq)
                .map(board -> baseRepository.save(Board.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .status(BoardStatus.UNREGISTERED)
                        .created(board.getCreated())
                        .build()))
                .map(board -> ResponseDto.OK(BoardDeleteResponseDto.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .status(board.getStatus())
                        .build()))
                .orElseThrow(BoardNotFoundException::new);
    }

    @Override
    public ResponseDto<BoardDetailResponseDto> detail(final Long seq) {
        return ResponseDto.OK(baseRepository.findById(seq)
                .map(board -> BoardDetailResponseDto.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .status(board.getStatus())
                        .created(board.getCreated())
                        .build())
                .orElseThrow(BoardNotFoundException::new));
    }

    @Override
    public ResponseDto<List<BoardListResponseDto>> list(Pageable pageable) {
        return ResponseDto.OK(boardRepository.findAllByStatus(pageable, BoardStatus.REGISTERED).stream()
                .map(board -> BoardListResponseDto.builder()
                        .seq(board.getSeq())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .created(board.getCreated())
                        .build())
                .collect(Collectors.toList()));
    }
}
