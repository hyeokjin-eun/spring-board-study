package com.study.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ServiceTestBaseConfig {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
