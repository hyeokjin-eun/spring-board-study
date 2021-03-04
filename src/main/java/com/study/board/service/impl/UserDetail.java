package com.study.board.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetail extends User {

    private com.study.board.model.entity.User user;

    public UserDetail(com.study.board.model.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getId(), user.getPassword(), authorities);
        this.user = user;
    }

    public com.study.board.model.entity.User getUser() {
        return user;
    }
}
