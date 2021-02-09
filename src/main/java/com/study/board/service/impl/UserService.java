package com.study.board.service.impl;

import com.study.board.model.dto.request.user.UserCreateRequestDto;
import com.study.board.model.dto.request.user.UserUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.user.*;
import com.study.board.model.entity.User;
import com.study.board.model.exception.UserNotFoundException;
import com.study.board.service.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<User, UserCreateRequestDto, UserUpdateRequestDto, UserListResponseDto, UserCreateResponseDto, UserUpdateResponseDto, UserDeleteResponseDto, UserDetailResponseDto> {

    @Override
    public ResponseDto<UserCreateResponseDto> create(final UserCreateRequestDto userCreateRequestDto) {
        return Optional.of(
                baseRepository.save(User.builder()
                        .id(userCreateRequestDto.getId())
                        .password(userCreateRequestDto.getPassword())
                        .email(userCreateRequestDto.getEmail())
                        .created(LocalDateTime.now())
                        .updated(LocalDateTime.now())
                        .build()))
                .map(user -> ResponseDto.OK(UserCreateResponseDto.builder()
                        .seq(user.getSeq())
                        .id(user.getId())
                        .email(user.getEmail())
                        .created(user.getCreated())
                        .build()))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ResponseDto<UserUpdateResponseDto> update(final UserUpdateRequestDto userUpdateRequestDto) {
        return baseRepository.findById(userUpdateRequestDto.getSeq())
                .map(user -> user
                        .setId(userUpdateRequestDto.getId())
                        .setPassword(userUpdateRequestDto.getPassword())
                        .setEmail(userUpdateRequestDto.getEmail())
                        .setUpdated(LocalDateTime.now()))
                .map(user -> baseRepository.save(user))
                .map(user -> ResponseDto.OK(UserUpdateResponseDto.builder()
                        .seq(user.getSeq())
                        .id(user.getId())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .created(user.getCreated())
                        .updated(user.getUpdated())
                        .build()))
                .orElseThrow(UserNotFoundException::new);
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
