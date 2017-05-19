package com.ysj.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yusijia
 * @Description:
 */
@Controller
public class Index2Controller {

    @GetMapping("/index2")
    public String index() {
        return "index2";
    }

}
