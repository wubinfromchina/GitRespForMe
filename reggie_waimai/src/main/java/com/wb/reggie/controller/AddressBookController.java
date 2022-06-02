package com.wb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.AddressBook;
import com.wb.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: 地址簿控制层
 * @Title: AddressBookController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 9:57
 */
@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增收货地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public RetObj<String> save(HttpSession session, @RequestBody AddressBook addressBook){
        log.info("准备保存");

        addressBook.setUserId((long)session.getAttribute("user"));
        addressBookService.save(addressBook);

        return RetObj.success("创建成功");
    }

    /**
     * 收货地址管理
     * @param session
     * @return
     */
    @GetMapping("/list")
    public RetObj<List<AddressBook>> list(HttpSession session){
        //获取移动端当前用户id
        long userId = (long)session.getAttribute("user");
        //条件构造器
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId,userId);
        wrapper.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> list = addressBookService.list(wrapper);

        return RetObj.success(list);
    }

    /**
     * 修改默认地址(返回默认地址数据)
     * @param addressBook
     * @return
     */
    @PutMapping("default")
    public RetObj<String> toDefault(@RequestBody AddressBook addressBook){
        //修改状态
        addressBookService.toDefault(addressBook);

        return RetObj.success("修改成功");
    }

    /**
     * 获取当前移动端用户的默认地址
     * @param session
     * @return
     */
    @GetMapping("default")
    public RetObj<AddressBook> getDefault(HttpSession session){
        //获取用户id
        long userId = (long)session.getAttribute("user");
        //获取默认地址
        AddressBook addressBook = addressBookService.getDefault(userId);

        return RetObj.success(addressBook);
    }


    /**
     * 回显数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RetObj<AddressBook> update(@PathVariable long id){

        AddressBook addressBook = addressBookService.getById(id);

        return RetObj.success(addressBook);
    }

    /**
     * 保存修改
     * @param addressBook
     * @return
     */
    @PutMapping
    public RetObj<String> putOne(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);

        return RetObj.success("修改成功");
    }

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public RetObj<String> delete(String ids){

        addressBookService.removeById(ids);

        return RetObj.success("删除成功");
    }

}
