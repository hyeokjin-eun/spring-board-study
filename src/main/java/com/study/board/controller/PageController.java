package com.study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("common/home");
    }

    @GetMapping("/main")
    public ModelAndView board() {
        return new ModelAndView("board/main");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("common/login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("common/register");
    }
}
