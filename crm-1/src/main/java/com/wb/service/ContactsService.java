package com.wb.service;

import com.wb.pojo.Contacts;

import java.util.List;

public interface ContactsService {

    List<Contacts> queryAllConByName(String contactsName);

    List<Contacts> queryAllContacts();
}
