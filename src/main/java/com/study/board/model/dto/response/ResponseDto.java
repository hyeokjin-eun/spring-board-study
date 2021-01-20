package com.study.board.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private LocalDateTime transactionTime;

    private String resultCode;

    private String description;

    private T data;

    public static <T> ResponseDto<T> OK() {
        return (ResponseDto<T>) ResponseDto.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    public static <T> ResponseDto<T> OK(T data) {
        return (ResponseDto<T>) ResponseDto.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> ERROR(String description) {
        return (ResponseDto<T>) ResponseDto.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}
