package com.wb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.reggie.pojo.AddressBook;

public interface AddressBookService extends IService<AddressBook> {
    void toDefault(AddressBook addressBook);

    AddressBook getDefault(long userId);
}
