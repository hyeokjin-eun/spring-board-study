package com.study.board.controller;

import com.study.board.model.dto.request.user.UserCreateRequestDto;
import com.study.board.model.dto.request.user.UserUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.user.UserCreateResponseDto;
import com.study.board.model.dto.response.user.UserDeleteResponseDto;
import com.study.board.model.dto.response.user.UserDetailResponseDto;
import com.study.board.model.dto.response.user.UserUpdateResponseDto;
import com.study.board.model.exception.UserNotFoundException;
import com.study.board.service.impl.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("게시판 Controller Test")
public class UserControllerTest extends ControllerTestBaseConfig {

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @CsvSource(value = {"'xptmxm1', 'xptmxm1', 'xptmxm1@email.com'", "'xptmxm2', 'xptmxm2', 'xptmxm2@email.com'"})
    @DisplayName("사용자 생성")
    public void createTest(String id, String password, String email) throws Exception {
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .id(id)
                .password(password)
                .email(email)
                .build();

        given(userService.create(any())).willReturn(ResponseDto.OK(UserCreateResponseDto.builder()
                .seq(1L)
                .id(id)
                .email(email)
                .created(LocalDateTime.now())
                .build()));

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).create(any());
    }

    @ParameterizedTest
    @CsvSource(value = {", 'xptmxm1', 'xptmxm1@email.com'", "'', 'xptmxm2', 'xptmxm2@email.com'"})
    @DisplayName("사용자 생성 ID 파라미터 검증")
    public void createIdParameterTest(String id, String password, String email) throws Exception {
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"'xptmxm1', , 'xptmxm1@email.com'", "'xptmxm2', '', 'xptmxm2@email.com'"})
    @DisplayName("사용자 생성 비밀 번호 파라미터 검증")
    public void createPasswordParameterTest(String id, String password, String email) throws Exception {
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"'xptmxm1', 'xptmxm1', ", "'xptmxm2', 'xptmxm2', ''"})
    @DisplayName("사용자 생성 이메일 파라미터 검증")
    public void createEmailParameterTest(String id, String password, String email) throws Exception {
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 'xptmxm1', 'xptmxm1', 'xptmxm1@email.com'", "2, 'xptmxm2', 'xptmxm2', 'xptmxm2@email.com'"})
    @DisplayName("사용자 수정")
    public void updateTest(Long seq, String id, String password, String email) throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .build();

        given(userService.update(any())).willReturn(ResponseDto.OK(UserUpdateResponseDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build()));

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.seq").value(seq))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.password").value(password))
                .andExpect(jsonPath("$.data.email").value(email));

        verify(userService).update(any());
    }

    @ParameterizedTest
    @CsvSource(value = {"0, 'xptmxm1', 'xptmxm1', 'xptmxm1@email.com'"})
    @DisplayName("사용자 수정 사용자 미존재")
    public void updateUserNotFound(Long seq, String id, String password, String email) throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .build();

        given(userService.update(any())).willThrow(UserNotFoundException.class);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));

        verify(userService).update(any());
    }

    @ParameterizedTest
    @CsvSource(value = {", 'xptmxm1', 'xptmxm1', 'xptmxm1@email.com'"})
    @DisplayName("사용자 수정 SEQ 파라미터 검증")
    public void updateSeqParameterTest(Long seq, String id, String password, String email) throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, , 'xptmxm1', 'xptmxm1@email.com'", "2, '', 'xptmxm2', 'xptmxm2@email.com'"})
    @DisplayName("사용자 수정 ID 파라미터 검증")
    public void updateIdParameterTest(Long seq, String id, String password, String email) throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 'xptmxm1', , 'xptmxm1@email.com'", "2, 'xptmxm2', '', 'xptmxm2@email.com'"})
    @DisplayName("사용자 수정 비밀 번호 파라미터 검증")
    public void updatePasswordParameterTest(Long seq, String id, String password, String email) throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 'xptmxm1', 'xptmxm1', ", "2, 'xptmxm2', 'xptmxm2', ''", "3, 'xptmxm3', 'xptmxm3', 'xptmxm3'"})
    @DisplayName("사용자 수정 비밀 번호 파라미터 검증")
    public void updateEmailParameterTest(Long seq, String id, String password, String email) throws Exception {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .seq(seq)
                .id(id)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"1"})
    @DisplayName("사용자 삭제")
    public void deleteTest(Long seq) throws Exception {
        given(userService.delete(seq)).willReturn(ResponseDto.OK(UserDeleteResponseDto.builder()
                .seq(seq)
                .id("delete1")
                .email("delete1@email.com")
                .updated(LocalDateTime.now())
                .build()));

        mockMvc.perform(delete("/user/" + seq))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.seq").value(seq));

        verify(userService).delete(seq);
    }

    @ParameterizedTest
    @CsvSource(value = {"0"})
    @DisplayName("사용자 삭제 사용자 미존재")
    public void deleteUserNotFound(Long seq) throws Exception {
        given(userService.delete(seq)).willThrow(UserNotFoundException.class);

        mockMvc.perform(delete("/user/" + seq))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));

        verify(userService).delete(seq);
    }

    @ParameterizedTest
    @CsvSource(value = {"1"})
    @DisplayName("사용자 상세")
    public void detailTest(Long seq) throws Exception {
        given(userService.detail(seq)).willReturn(ResponseDto.OK(UserDetailResponseDto.builder()
                .seq(seq)
                .id("detail1")
                .password("detail1")
                .email("detail1@email.com")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build()));

        mockMvc.perform(get("/user/" + seq))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.seq").value(seq));

        verify(userService).detail(seq);
    }
}