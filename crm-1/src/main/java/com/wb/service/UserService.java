package com.wb.service;

import com.wb.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UserService {
    User selectByIdAndPwd(Map<String,Object> map);

    List<User> selectAllUser();

    User queryByPrimaryKey(String id);
}
