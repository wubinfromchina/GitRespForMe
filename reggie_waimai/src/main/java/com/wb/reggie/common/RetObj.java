package com.wb.reggie.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 返回前端的实体类
 * @Title: RetObj
 * @Package com.wb.reggie.common
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/25 22:17
 */
@Data
public class RetObj<T> implements Serializable {//序列化接口

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> RetObj<T> success(T object) {
        RetObj<T> r = new RetObj<T>();
        r.data = object;
        r.code = Contents.SUCCESS_CODE;
        return r;
    }

    public static <T> RetObj<T> error(String msg) {
        RetObj r = new RetObj();
        r.msg = msg;
        r.code = Contents.FAILED_CODE;
        return r;
    }

    public RetObj<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
