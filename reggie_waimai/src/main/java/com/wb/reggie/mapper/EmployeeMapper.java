package com.wb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wb.reggie.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: 登录功能
 * @Title: EmployeeMapper
 * @Package com.wb.reggie.mapper
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/25 21:53
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee>{

}
