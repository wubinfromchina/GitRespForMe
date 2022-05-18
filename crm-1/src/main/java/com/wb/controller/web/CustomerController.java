package com.wb.controller.web;

import com.wb.pojo.Customer;
import com.wb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/customer/index.do")
    public String index(HttpServletRequest request){
        List<Customer> customerList = customerService.queryAllC();
        request.setAttribute("customerList",customerList);

        return "workbench/customer/index";
    }
}
