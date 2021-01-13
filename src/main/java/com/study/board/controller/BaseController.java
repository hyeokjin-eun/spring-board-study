package com.study.board.controller;

import com.study.board.model.front.AdminPage;
import com.study.board.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
public abstract class BaseController<Entity, ListRes> {

    protected BaseService<Entity, ListRes> baseService;

    @Autowired
    public void setBaseService(BaseService<Entity, ListRes> baseService) {
        this.baseService = baseService;
    }

    @GetMapping("")
    public ModelAndView list(@PageableDefault(sort = "seq", direction = Sort.Direction.ASC, size = 20) Pageable pageable) {
        AdminPage<List<ListRes>> adminPage = baseService.list(pageable);
        return new ModelAndView(adminPage.getUrl())
                .addObject("list", adminPage.getData());
    }
}
