package com.study.board.controller;

import com.study.board.model.dto.response.ResponseDto;
import com.study.board.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
public abstract class BaseController<Entity, CreateReq, UpdateReq, ListRes, CreateRes, UpdateRes, DeleteRes, DetailRes> {

    protected BaseService<Entity, CreateReq, UpdateReq, ListRes, CreateRes, UpdateRes, DeleteRes, DetailRes> baseService;

    @Autowired
    public void setBaseService(BaseService<Entity, CreateReq, UpdateReq, ListRes, CreateRes, UpdateRes, DeleteRes, DetailRes> baseService) {
        this.baseService = baseService;
    }

    @PostMapping("")
    public ResponseDto<CreateRes> create(@Valid @RequestBody final CreateReq createReq) {
        return baseService.create(createReq);
    }

    @PutMapping("")
    public ResponseDto<UpdateRes> update(@Valid @RequestBody final UpdateReq updateReq) {
        return baseService.update(updateReq);
    }

    @DeleteMapping("{seq}")
    public ResponseDto<DeleteRes> delete(@PathVariable final Long seq) {
        return baseService.delete(seq);
    }

    @GetMapping("{seq}")
    public ResponseDto<DetailRes> detail(@PathVariable final Long seq) {
        return baseService.detail(seq);
    }

    @GetMapping("")
    public ResponseDto<List<ListRes>> list(@PageableDefault(sort = "seq", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return baseService.list(pageable);
    }
}
