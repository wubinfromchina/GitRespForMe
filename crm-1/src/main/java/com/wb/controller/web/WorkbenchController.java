package com.wb.controller.web;

import com.wb.pojo.User;
import com.wb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class WorkbenchController {
    @Autowired
    UserService userService;

    @RequestMapping("/workbench/toWorkbenchIndex.do")
    public String index(){

        return "workbench/index";
    }
}
