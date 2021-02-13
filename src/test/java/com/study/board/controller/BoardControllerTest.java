package com.study.board.controller;

import com.study.board.model.dto.request.board.BoardCreateRequestDto;
import com.study.board.model.dto.request.board.BoardUpdateRequestDto;
import com.study.board.model.dto.response.ResponseDto;
import com.study.board.model.dto.response.board.*;
import com.study.board.model.enumClass.BoardStatus;
import com.study.board.model.exception.BoardNotFoundException;
import com.study.board.service.impl.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        @ParameterizedTest
        @CsvSource(value = {"'TITLE1', 'CONTENT1'", "'TITLE2', 'CONTENT2'"})
        @DisplayName("정상 동작")
        public void createTest(String title, String content) throws Exception {
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
        @DisplayName("제목 파라미터 검증")
        public void createTitleParameterTest(String title, String content) throws Exception {
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
        @DisplayName("내용 파라미터 검증")
        public void createContentParameterTest(String title, String content) throws Exception {
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
    }

    @Nested
    @DisplayName("게시글 수정")
    class update {
        @ParameterizedTest
        @CsvSource(value = {"1, 'TITLE UPDATE 1', 'CONTENT UPDATE 1', 'UNREGISTERED'"})
        @DisplayName("정상 동작")
        public void updateTest(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
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
        @CsvSource(value = {"0, 'TITLE UPDATE 1', 'CONTENT UPDATE 1', 'UNREGISTERED'"})
        @DisplayName("게시글 미존재")
        public void updateBoardNotFound(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
            BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                    .seq(seq)
                    .title(title)
                    .content(content)
                    .status(boardStatus)
                    .build();

            given(boardService.update(any())).willThrow(BoardNotFoundException.class);

            mockMvc.perform(put("/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            verify(boardService).update(any());
        }

        @ParameterizedTest
        @CsvSource(value = {", 'TITLE UPDATE 1', 'CONTENT UPDATE 1', 'UNREGISTERED'"})
        @DisplayName("SEQ 파라미터 검증")
        public void updateSeqParameterTest(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
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
        @DisplayName("제목 파라미터 검증")
        public void updateTitleParameterTest(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
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
        @DisplayName("내용 파라미터 검증")
        public void updateContentParameterTest(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
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
        @DisplayName("상태 파라미터 검증")
        public void updateStatusParameterTest(Long seq, String title, String content, BoardStatus boardStatus) throws Exception {
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
