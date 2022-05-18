package com.wb.service.impl;

import com.wb.mapper.UserMapper;
import com.wb.pojo.User;
import com.wb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByIdAndPwd(Map<String, Object> map) {
        return userMapper.selectByIdAndPwd(map);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public User queryByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
