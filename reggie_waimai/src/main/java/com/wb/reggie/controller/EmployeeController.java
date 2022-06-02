package com.wb.reggie.controller;

import com.alibaba.druid.pool.WrapperAdapter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.common.ThreadLocalContext;
import com.wb.reggie.pojo.Employee;
import com.wb.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description: 员工控制层
 * @Title: EmployeeController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/25 22:02
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    /*
    * 登录
    * */
    @PostMapping("/login")
    public RetObj<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //获取session
        HttpSession session = request.getSession();
        //加密密码
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //查询用户信息
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,username);
        Employee one = employeeService.getOne(wrapper);
        if (one == null || !password.equals(one.getPassword())){
            return RetObj.error("用户名或密码错误，请重试");
        }else if (one.getStatus() != 1){
            return RetObj.error("账号已过期，请联系管理人员核实");
        }else {
            session.setAttribute("employee",one.getId());
            return RetObj.success(one);
        }

    }
    /*
    * 退出
    * */
    @PostMapping("/logout")
    public RetObj logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return RetObj.success("退出成功");
    }

    /*
    * 分页查询
    * */
    @GetMapping("/page")
    public RetObj<Page> page(Integer page, Integer pageSize, String name){
        //log.info("page={},pageSize={},name={}",page,pageSize,name);
        //分页构造器
        Page pageInfo = new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper();
        //查询条件
        wrapper.like(name!=null,Employee::getName,name);
        wrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,wrapper);
        return RetObj.success(pageInfo);
    }

    /*
    * 新增员工
    * */
    @PostMapping
    public RetObj<String> addEmployee(HttpSession session,@RequestBody Employee employee){
        //补全员工信息
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //employee.setCreateUser((long)session.getAttribute("employee"));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser((long)session.getAttribute("employee"));
        //添加员工
        employeeService.save(employee);
        return RetObj.success("添加成功");
    }

    /**
     * 更新员工状态
     * @param session
     * @param employee
     * @return
     */
    @PutMapping
    public RetObj<String> update(HttpSession session,@RequestBody Employee employee){
        //log.info("{}的状态信息发生改变",employee.getId());
        //参数设置
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser((long)session.getAttribute("employee"));
        log.info("当前用户：{}", ThreadLocalContext.getId());

        //long id = Thread.currentThread().getId();
        //log.info("当前线程id为：{}",id);
        //更新
        employeeService.updateById(employee);
        return RetObj.success("修改成功");
    }

    @GetMapping("/{id}")
    public RetObj<Employee> getOneById(@PathVariable Long id){
        //log.info("携带数据id={}",id);
        Employee employee = employeeService.getById(id);

        return RetObj.success(employee);
    }
}
