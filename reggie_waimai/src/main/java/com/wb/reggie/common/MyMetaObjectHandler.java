package com.wb.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description: 公共字段自动填充
 * @Title: MyMetaObjectHandler
 * @Package com.wb.reggie.common
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 10:56
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(metaObject.toString());
        log.info("当前用户：{}",ThreadLocalContext.getId());
        //填充参数
        //未知问题，无法设置参数：metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",ThreadLocalContext.getId());
        metaObject.setValue("createUser",ThreadLocalContext.getId());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createTime", LocalDateTime.now());

    }

    /**
     * 修改自动填充（正常）
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //long id = Thread.currentThread().getId();
        log.info("详细数据为：{}",metaObject.toString());
        log.info("当前用户：{}",ThreadLocalContext.getId());
        //填充参数
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",ThreadLocalContext.getId());
    }
}
