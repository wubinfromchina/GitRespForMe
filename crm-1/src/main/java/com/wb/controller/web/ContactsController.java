package com.wb.controller.web;

import com.wb.pojo.Contacts;
import com.wb.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ContactsController {
    @Autowired
    private ContactsService contactsService;

    @RequestMapping("/workbench/contacts/index.do")
    public String index(HttpServletRequest request){
        List<Contacts> contactsList = contactsService.queryAllContacts();
        request.setAttribute("contactsList",contactsList);
        return "workbench/contacts/index";
    }
}
