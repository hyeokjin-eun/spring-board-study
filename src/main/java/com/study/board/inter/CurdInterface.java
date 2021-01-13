package com.study.board.inter;

import com.study.board.model.front.AdminPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurdInterface<ListRes> {
    AdminPage<List<ListRes>> list(Pageable pageable);
}
