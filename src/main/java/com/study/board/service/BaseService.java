package com.study.board.service;

import com.study.board.inter.CurdInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService<Entity, ListRes> implements CurdInterface<ListRes> {

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;
}
