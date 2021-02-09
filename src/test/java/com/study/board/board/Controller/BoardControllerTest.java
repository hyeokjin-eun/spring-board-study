package com.study.board.board.Controller;

import com.study.board.ControllerTestBaseConfig;
import com.study.board.controller.BoardController;
import com.study.board.model.dto.request.board.BoardCreateRequestDto;
import com.study.board.model.dto.request.board.BoardUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.board.BoardCreateResponseDto;
import com.study.board.model.dto.response.board.BoardDeleteResponseDto;
import com.study.board.model.dto.response.board.BoardDetailResponseDto;
import com.study.board.model.dto.response.board.BoardUpdateResponseDto;
import com.study.board.model.enumClass.BoardStatus;
import com.study.board.service.impl.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@DisplayName("게시판 Controller Test")
public class BoardControllerTest extends ControllerTestBaseConfig {

    @MockBean
    private BoardService boardService;

    @ParameterizedTest
    @CsvSource(value = {"'TITLE1', 'CONTENT1'", "'TITLE2', 'CONTENT2'"})
    public void 게시글_생성(String title, String content) throws Exception {
        BoardCreateRequestDto boardCreateRequestDto = BoardCreateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        given(boardService.create(any())).willReturn(ResponseDto.OK(BoardCreateResponseDto.builder()
                .seq(1L)
                .title(title)
                .content(content)
                .status(BoardStatus.REGISTERED)
                .created(LocalDateTime.now())
                .build()));

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(title))
                .andExpect(jsonPath("$.data.content").value(content));

        verify(boardService).create(any());
    }

    @ParameterizedTest
    @CsvSource(value = {"'', 'CONTENT1'", ", 'CONTENT2'"})
    public void 게시글_생성_제목_파라미터_검증(String title, String content) throws Exception {
        BoardCreateRequestDto boardCreateRequestDto = BoardCreateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));
    }

    @ParameterizedTest
    @CsvSource(value = {"'TITLE1', ''", "'TITLE2', "})
    public void 게시글_생성_내용_파라미터_검증(String title, String content) throws Exception {
        BoardCreateRequestDto boardCreateRequestDto = BoardCreateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 'TITLE UPDATE 1', 'CONTENT UPDATE 1', 'UNREGISTERED'"})
    public void 게시글_수정(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .status(boardStatus)
                .build();

        given(boardService.update(any())).willReturn(ResponseDto.OK(BoardUpdateResponseDto.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .status(boardStatus)
                .created(LocalDateTime.now())
                .build()));

        mockMvc.perform(put("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.seq").value(seq))
                .andExpect(jsonPath("$.data.title").value(title))
                .andExpect(jsonPath("$.data.content").value(content))
                .andExpect(jsonPath("$.data.status").value(boardStatus.toString()));

        verify(boardService).update(any());
    }

    @ParameterizedTest
    @CsvSource(value = {", 'TITLE UPDATE 1', 'CONTENT UPDATE 1', 'UNREGISTERED'"})
    public void 게시글_수정_SEQ_파라미터_검증(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .status(boardStatus)
                .build();

        mockMvc.perform(put("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, , 'CONTENT UPDATE 1', 'UNREGISTERED'", "1, '', 'CONTENT UPDATE 1', 'UNREGISTERED'"})
    public void 게시글_수정_제목_파라미터_검증(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .status(boardStatus)
                .build();

        mockMvc.perform(put("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 'TITLE UPDATE 1', , 'UNREGISTERED'", "1, 'TITLE UPDATE 1', '', 'UNREGISTERED'"})
    public void 게시글_수정_내용_파라미터_검증(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .status(boardStatus)
                .build();

        mockMvc.perform(put("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 'TITLE UPDATE 1', 'CONTENT UPDATE 1', "})
    public void 게시글_수정_상태_파라미터_검증(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .status(boardStatus)
                .build();

        mockMvc.perform(put("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value("ERROR"));
    }

    @ParameterizedTest
    @CsvSource(value = {"1"})
    public void 게시글_삭제(Long seq) throws Exception{
        given(boardService.delete(seq)).willReturn(ResponseDto.OK(BoardDeleteResponseDto.builder()
                .seq(seq)
                .title("TITLE DELETE 1")
                .content("TITLE DELETE 1")
                .status(BoardStatus.UNREGISTERED)
                .build()));

        mockMvc.perform(delete("/board/" + seq))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.seq").value(seq))
                .andExpect(jsonPath("$.data.status").value(BoardStatus.UNREGISTERED.toString()));

        verify(boardService).delete(seq);
    }

    @ParameterizedTest
    @CsvSource(value = {"1"})
    public void 게시글_상세(Long seq) throws Exception {
        given(boardService.detail(seq)).willReturn(ResponseDto.OK(BoardDetailResponseDto.builder()
                .seq(seq)
                .title("TITLE DETAIL 1")
                .content("TITLE DETAIL 1")
                .status(BoardStatus.UNREGISTERED)
                .created(LocalDateTime.now())
                .build()));

        mockMvc.perform(get("/board/" + seq))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.seq").value(seq));

        verify(boardService).detail(seq);
    }
}
