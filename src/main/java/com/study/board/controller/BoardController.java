package com.study.board.controller;

import com.study.board.model.dto.response.BoardListResponseDto;
import com.study.board.model.entity.Board;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardController extends BaseController<Board, BoardListResponseDto>{
}
