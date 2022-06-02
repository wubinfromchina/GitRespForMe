package com.wb.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: 主启动类
 * @Title: ReggieApplication
 * @Package com.wb.reggie
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/25 21:11
 */
@Slf4j
@ServletComponentScan//扫描过滤器类上的注解
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching//开启注解缓存功能
public class ReggieApplication {
    public static void main(String[] args) {
        log.info("程序启动");
        SpringApplication.run(ReggieApplication.class,args);
        log.info("程序启动成功");
    }
}
