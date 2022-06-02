package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.mapper.EmployeeMapper;
import com.wb.reggie.pojo.Employee;
import com.wb.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @Description: 管理员登录Service层实现类
 * @Title: EmployeeServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/25 22:00
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

}
