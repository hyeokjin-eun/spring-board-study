package com.study.board.controller;

import com.study.board.model.dto.request.board.BoardCreateRequestDto;
import com.study.board.model.dto.request.board.BoardUpdateRequestDto;
import com.study.board.model.dto.response.board.*;
import com.study.board.model.entity.Board;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("board")
public class BoardController extends BaseController<Board, BoardCreateRequestDto, BoardUpdateRequestDto, BoardListResponseDto, BoardCreateResponseDto, BoardUpdateResponseDto, BoardDeleteResponseDto, BoardDetailResponseDto>{
}
