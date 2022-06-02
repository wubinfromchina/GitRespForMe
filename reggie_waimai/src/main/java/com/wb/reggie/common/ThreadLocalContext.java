package com.wb.reggie.common;

/**
 * @Description: 存储当前线程用户id的工具类
 * @Title: ThreadLocalContext
 * @Package com.wb.reggie.common
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 14:08
 */
public class ThreadLocalContext {
    //定义为私有静态
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置线程局部变量ThreadLocal为入参id（传入用户主键）
     */
    public static void setId(long id){
        threadLocal.set(id);
    }

    /**
     * 获取主键
     * @return
     */
    public static long getId(){
        return threadLocal.get();
    }
}
