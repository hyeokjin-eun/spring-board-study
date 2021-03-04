package com.study.board.service.impl;

import com.study.board.model.exception.UserNotFoundException;
import com.study.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findUserById(id)
                .map(user -> {
                    List<GrantedAuthority> roles = new ArrayList<>();
                    roles.add(new SimpleGrantedAuthority(user.getRole().getCode()));
                    return new UserDetail(user, roles);
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
