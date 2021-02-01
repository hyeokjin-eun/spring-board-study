package com.study.board.service.impl;

import com.study.board.model.dto.request.user.UserCreateRequestDto;
import com.study.board.model.dto.request.user.UserUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.user.*;
import com.study.board.model.entity.User;
import com.study.board.service.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User, UserCreateRequestDto, UserUpdateRequestDto, UserListResponseDto, UserCreateResponseDto, UserUpdateResponseDto, UserDeleteResponseDto, UserDetailResponseDto> {

    @Override
    public ResponseDto<UserCreateResponseDto> create(UserCreateRequestDto userCreateRequestDto) {
        return null;
    }

    @Override
    public ResponseDto<UserUpdateResponseDto> update(UserUpdateRequestDto userUpdateRequestDto) {
        return null;
    }

    @Override
    public ResponseDto<UserDeleteResponseDto> delete(Long seq) {
        return null;
    }

    @Override
    public ResponseDto<UserDetailResponseDto> detail(Long seq) {
        return null;
    }

    @Override
    public ResponseDto<List<UserListResponseDto>> list(Pageable pageable) {
        return null;
    }
}
