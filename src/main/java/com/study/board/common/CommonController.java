package com.study.board.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {

    @ResponseBody
    @GetMapping("/health/check")
    public String healthCheck() {
        return "OK";
    }
}
