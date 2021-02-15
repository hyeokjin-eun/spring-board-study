package com.study.board.controller;

import com.study.board.model.dto.request.board.BoardCreateRequestDto;
import com.study.board.model.dto.request.board.BoardUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.board.*;
import com.study.board.model.enumClass.BoardStatus;
import com.study.board.model.exception.BoardNotFoundException;
import com.study.board.service.impl.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Nested
    @DisplayName("게시글 생성")
    class create {
        private BoardCreateRequestDto boardCreateRequestDto;

        @BeforeEach
        public void setUp() {
            boardCreateRequestDto = BoardCreateRequestDto.builder()
                    .title("TITLE1")
                    .content("CONTENT1")
                    .build();
        }

        @Test
        @DisplayName("정상 동작")
        public void createTest() throws Exception {
            given(boardService.create(any())).willReturn(ResponseDto.OK(BoardCreateResponseDto.builder()
                    .seq(1L)
                    .title(boardCreateRequestDto.getTitle())
                    .content(boardCreateRequestDto.getContent())
                    .status(BoardStatus.REGISTERED)
                    .created(LocalDateTime.now())
                    .build()));

            mockMvc.perform(post("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value(boardCreateRequestDto.getTitle()))
                    .andExpect(jsonPath("$.data.content").value(boardCreateRequestDto.getContent()));

            verify(boardService).create(any());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("제목 파라미터 검증")
        public void createTitleParameterTest(String title) throws Exception {
            boardCreateRequestDto.setTitle(title);

            mockMvc.perform(post("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("내용 파라미터 검증")
        public void createContentParameterTest(String content) throws Exception {
            boardCreateRequestDto.setContent(content);

            mockMvc.perform(post("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardCreateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class update {
        private BoardUpdateRequestDto boardUpdateRequestDto;

        @BeforeEach
        public void setUp() {
            boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                    .seq(1L)
                    .title("TITLE UPDATE 1")
                    .content("CONTENT UPDATE 1")
                    .status(BoardStatus.UNREGISTERED)
                    .build();
        }

        @Test
        @DisplayName("정상 동작")
        public void updateTest() throws Exception {
            given(boardService.update(any())).willReturn(ResponseDto.OK(BoardUpdateResponseDto.builder()
                    .seq(boardUpdateRequestDto.getSeq())
                    .title(boardUpdateRequestDto.getTitle())
                    .content(boardUpdateRequestDto.getContent())
                    .status(boardUpdateRequestDto.getStatus())
                    .created(LocalDateTime.now())
                    .build()));

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.seq").value(boardUpdateRequestDto.getSeq()))
                    .andExpect(jsonPath("$.data.title").value(boardUpdateRequestDto.getTitle()))
                    .andExpect(jsonPath("$.data.content").value(boardUpdateRequestDto.getContent()))
                    .andExpect(jsonPath("$.data.status").value(boardUpdateRequestDto.getStatus().toString()));

            verify(boardService).update(any());
        }

        @ParameterizedTest
        @CsvSource(value = {"0"})
        @DisplayName("게시글 미존재")
        public void updateBoardNotFound(Long seq) throws Exception {
            boardUpdateRequestDto.setSeq(seq);

            given(boardService.update(any())).willThrow(BoardNotFoundException.class);

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(boardService).update(any());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("SEQ 파라미터 검증")
        public void updateSeqParameterTest(Long seq) throws Exception {
            boardUpdateRequestDto.setSeq(seq);

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("제목 파라미터 검증")
        public void updateTitleParameterTest(String title) throws Exception {
            boardUpdateRequestDto.setTitle(title);

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("내용 파라미터 검증")
        public void updateContentParameterTest(String content) throws Exception {
            boardUpdateRequestDto.setContent(content);

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("상태 파라미터 검증")
        public void updateStatusParameterTest(BoardStatus boardStatus) throws Exception {
            boardUpdateRequestDto.setStatus(boardStatus);

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class delete {
        @ParameterizedTest
        @CsvSource(value = {"1"})
        @DisplayName("정상 동작")
        public void deleteTest(Long seq) throws Exception{
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
        @CsvSource(value = {"0"})
        @DisplayName("게시글 미존재")
        public void deleteBoardNotFound(Long seq) throws Exception {
            given(boardService.delete(seq)).willThrow(BoardNotFoundException.class);

            mockMvc.perform(delete("/board/" + seq))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(boardService).delete(seq);
        }
    }

    @Nested
    @DisplayName("게시글 상세")
    class detail {
        @ParameterizedTest
        @CsvSource(value = {"1"})
        @DisplayName("정상 동작")
        public void detailTest(Long seq) throws Exception {
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

        @ParameterizedTest
        @CsvSource(value = {"0"})
        @DisplayName("게시글 미존재")
        public void detailBoardNotFound(Long seq) throws Exception{
            given(boardService.detail(seq)).willThrow(BoardNotFoundException.class);

            mockMvc.perform(get("/board/" + seq))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(boardService).detail(seq);
        }
    }

    @Nested
    @DisplayName("게시글 목록")
    class list {
        @Test
        @DisplayName("정상 동작")
        public void listTest() throws Exception {
            List<BoardListResponseDto> boardListResponseDtoList = Stream.iterate(1, index -> index + 1)
                    .map(index -> BoardListResponseDto.builder()
                            .seq((long) index)
                            .title("TITLE " + index)
                            .content("CONTENT " + index)
                            .created(LocalDateTime.now())
                            .build())
                    .limit(10)
                    .sorted(Comparator.comparing(BoardListResponseDto::getSeq).reversed())
                    .collect(Collectors.toList());

            given(boardService.list(any())).willReturn(ResponseDto.OK(boardListResponseDtoList));

            mockMvc.perform(get("/board")
                    .param("page", "0"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.length()").value(boardListResponseDtoList.size()));

            verify(boardService).list(any());
        }
    }
}
