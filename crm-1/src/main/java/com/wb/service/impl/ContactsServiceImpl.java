package com.wb.service.impl;

import com.wb.mapper.ContactsMapper;
import com.wb.pojo.Contacts;
import com.wb.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Override
    public List<Contacts> queryAllConByName(String contactsName) {
        return contactsMapper.selectAllConByName(contactsName);
    }

    @Override
    public List<Contacts> queryAllContacts() {
        return contactsMapper.selectAllContacts();
    }
}
