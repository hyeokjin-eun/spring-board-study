package com.study.board.controller;

import com.study.board.model.dto.request.user.UserCreateRequestDto;
import com.study.board.model.dto.request.user.UserUpdateRequestDto;
import com.study.board.model.dto.response.user.*;
import com.study.board.model.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<User, UserCreateRequestDto, UserUpdateRequestDto, UserListResponseDto, UserCreateResponseDto, UserUpdateResponseDto, UserDeleteResponseDto, UserDetailResponseDto>{
}
