package com.study.board.inter;

import com.study.board.model.dto.response.ResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurdInterface<CreateReq, UpdateReq, ListRes, CreateRes, UpdateRes, DeleteRes, DetailRes> {
    ResponseDto<CreateRes> create(final CreateReq createReq);

    ResponseDto<UpdateRes> update(final UpdateReq updateReq);

    ResponseDto<DeleteRes> delete(final Long seq);

    ResponseDto<DetailRes> detail(final Long seq);

    ResponseDto<List<ListRes>> list(Pageable pageable);
}
