package com.wb.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Description: 全局异常捕获处理器
 * @Title: GlobalExceptionHandler
 * @Package com.wb.reggie.common
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/26 20:09
 */
@ControllerAdvice(annotations = {RestController.class,Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * sql异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public RetObj<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error(e.getMessage());
        if (e.getMessage().contains("Duplicate entry")){
            String[] s = e.getMessage().split(" ");
            return RetObj.error(s[2] + "已存在");
        }
        return RetObj.error("系统繁忙，请重试");
    }

    /**
     * 删除菜品分类或套餐分类异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(RuiJiException.class)
    public RetObj<String> exceptionHandler(RuiJiException e){
        log.error(e.getMessage());

        return RetObj.error(e.getMessage());
    }
}
