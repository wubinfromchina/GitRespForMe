package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.mapper.AddressBookMapper;
import com.wb.reggie.pojo.AddressBook;
import com.wb.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 地址簿业务逻辑层
 * @Title: AddressBookServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 9:55
 */
@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {


    /**
     * 修改默认地址
     * @param addressBook
     */
    @Override
    @Transactional
    public void toDefault(AddressBook addressBook) {
        //修改之前的默认地址为普通地址
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getIsDefault,1);
        AddressBook one = super.getOne(wrapper);
        if (one != null){
            one.setIsDefault(0);
            super.updateById(one);
        }
        //修改后的默认地址
        //填充属性
        addressBook = super.getById(addressBook.getId());
        //修改状态
        addressBook.setIsDefault(1);
        //保存
        super.updateById(addressBook);

    }

    /**
     * 获取默认地址详细信息
     * @param userId
     * @return
     */
    @Override
    public AddressBook getDefault(long userId) {
        //条件构造器
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getIsDefault,1);
        //返回默认地址
        return super.getOne(wrapper);
    }
}
