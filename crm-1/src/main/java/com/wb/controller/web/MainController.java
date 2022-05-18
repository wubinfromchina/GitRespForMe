package com.wb.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/workbench/main/toIndex.do")
    public String index(){

        return "/workbench/main/index";
    }
}
