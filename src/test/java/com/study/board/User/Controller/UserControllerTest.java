package com.study.board.User.Controller;

import com.study.board.ControllerTestBaseConfig;
import com.study.board.controller.UserController;
import com.study.board.model.dto.request.user.UserCreateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.user.UserCreateResponseDto;
import com.study.board.service.impl.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends ControllerTestBaseConfig {

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @CsvSource(value = {"'xptmxm1', 'xptmxm1', 'xptmxm1@email.com'", "'xptmxm2', 'xptmxm2', 'xptmxm2@email.com'"})
    public void 사용자_생성(String id, String password, String email) throws Exception {
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
}
