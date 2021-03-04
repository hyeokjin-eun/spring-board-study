package com.study.board.service;

import com.study.board.model.dto.request.user.UserCreateRequestDto;
import com.study.board.model.dto.request.user.UserUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.user.UserCreateResponseDto;
import com.study.board.model.dto.response.user.UserUpdateResponseDto;
import com.study.board.model.entity.User;
import com.study.board.model.enumClass.UserRole;
import com.study.board.repository.UserRepository;
import com.study.board.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("사용자 Service Test")
public class UserServiceTest extends ServiceTestBaseConfig {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("사용자 생성")
    class create {
        private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        private UserCreateRequestDto userCreateRequestDto;

        @BeforeEach
        public void setUp() {
            userCreateRequestDto = UserCreateRequestDto.builder()
                    .id("xptmxm1")
                    .password("xptmxm1")
                    .email("xptmxm1@email.com")
                    .role(UserRole.ROLE_USER)
                    .username("xptmxm1")
                    .build();
        }

        @Test
        @DisplayName("정상 동작")
        public void createTest() {
            given(passwordEncoder.encode(any())).willReturn(encoder.encode("xptmxm1"));

            given(userService.baseRepository.save(any(User.class))).willReturn(User.builder()
                    .seq(1L)
                    .id("xptmxm1")
                    .password(encoder.encode("xptmxm1"))
                    .email("xptmxm1@email.com")
                    .role(UserRole.ROLE_USER)
                    .username("xptmxm1")
                    .created(LocalDateTime.now())
                    .updated(LocalDateTime.now())
                    .build());

            ResponseDto<UserCreateResponseDto> responseDto = userService.create(userCreateRequestDto);

            verify(userRepository).save(any(User.class));
            assertThat(responseDto).isNotNull();
            assertThat(responseDto.getData().getSeq()).isEqualTo(1L);
            assertThat(responseDto.getData().getId()).isEqualTo(userCreateRequestDto.getId());
            assertThat(responseDto.getData().getEmail()).isEqualTo(userCreateRequestDto.getEmail());
            assertThat(responseDto.getData().getRole()).isEqualTo(userCreateRequestDto.getRole());
            assertThat(responseDto.getData().getUsername()).isEqualTo(userCreateRequestDto.getUsername());
        }
    }

    @Nested
    @DisplayName("사용자 수정")
    class update {

        private UserUpdateRequestDto userUpdateRequestDto;

        private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        @BeforeEach
        public void setUp() {
            userUpdateRequestDto = UserUpdateRequestDto.builder()
                    .seq(1L)
                    .id("xptmxm1update")
                    .password("xptmxm1update")
                    .email("xptmxm1update@email.com")
                    .role(UserRole.ROLE_USER)
                    .username("xptmxm1update")
                    .build();
        }

        @Test
        @DisplayName("정상 동작")
        public void updateTest() {
            given(userRepository.findById(any(Long.class))).willReturn(Optional.of(User.builder()
                    .seq(1L)
                    .id("xptmxm1")
                    .password(encoder.encode("xptmxm1"))
                    .email("xptmxm1@email.com")
                    .role(UserRole.ROLE_USER)
                    .username("xptmxm1")
                    .created(LocalDateTime.now())
                    .updated(LocalDateTime.now())
                    .build()));

            given(userService.baseRepository.save(any(User.class))).willReturn(User.builder()
                    .seq(1L)
                    .id(userUpdateRequestDto.getId())
                    .password(encoder.encode(userUpdateRequestDto.getPassword()))
                    .email(userUpdateRequestDto.getEmail())
                    .role(userUpdateRequestDto.getRole())
                    .username(userUpdateRequestDto.getUsername())
                    .created(LocalDateTime.now())
                    .updated(LocalDateTime.now())
                    .build());

            ResponseDto<UserUpdateResponseDto> responseDto = userService.update(userUpdateRequestDto);

            verify(userService.baseRepository).save(any());
            verify(userService.baseRepository).findById(any());
            assertThat(responseDto.getData().getSeq()).isEqualTo(userUpdateRequestDto.getSeq());
            assertThat(responseDto.getData().getId()).isEqualTo(userUpdateRequestDto.getId());
            assertThat(encoder.matches(userUpdateRequestDto.getPassword(), responseDto.getData().getPassword())).isTrue();
            assertThat(responseDto.getData().getEmail()).isEqualTo(userUpdateRequestDto.getEmail());
            assertThat(responseDto.getData().getRole()).isEqualTo(userUpdateRequestDto.getRole());
            assertThat(responseDto.getData().getUsername()).isEqualTo(userUpdateRequestDto.getUsername());
        }

        @Test
        @DisplayName("사용자 미존재")
        public void updateUserNotFound() {
            given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

            ResponseDto<UserUpdateResponseDto> responseDto = userService.update(userUpdateRequestDto);
        }
    }
}
