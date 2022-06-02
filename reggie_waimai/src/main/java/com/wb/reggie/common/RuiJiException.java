package com.wb.reggie.common;

/**
 * @Description: 自定义异常
 * @Title: DeleteFailedException
 * @Package com.wb.reggie.common
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 19:32
 */
public class RuiJiException extends RuntimeException{

    public RuiJiException() {super();
    }

    public RuiJiException(String message) {
        super(message);
    }
}
