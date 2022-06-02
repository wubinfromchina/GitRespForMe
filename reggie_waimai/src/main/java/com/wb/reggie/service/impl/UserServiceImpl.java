package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.mapper.UserMapper;
import com.wb.reggie.pojo.User;
import com.wb.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户业务逻辑
 * @Title: UserServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 8:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
