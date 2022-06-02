package com.wb.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.common.ThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 登录验证过滤器
 * @Title: LoginFilter
 * @Package com.wb.reggie.filter
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/26 17:47
 */
@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    //请求地址比较的工具对象
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //拦截的地址
        String requestURI = request.getRequestURI();
        //判断请求是否属于非拦截请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        //属于放行路径则直接放行，否则进一步判断
       boolean flag = checkURI(urls,requestURI);
       if (flag){
           filterChain.doFilter(request,response);
           return;
       }
        //输出拦截的地址日志
        log.info("监控到请求：{} 访问", requestURI);
        //判断客户端是否已经登录过,否则跳转到登录页面,是则放行
        Object employee = request.getSession().getAttribute("employee");
        if (employee != null){
            //在线程成员变量ThreadLocal中存入id
            long id = (long)employee;
            ThreadLocalContext.setId(id);
            log.info("当前用户id为：{}",id);
            //跳转
            filterChain.doFilter(request,response);
            return;
        }
        //判断用户端是否已经登录过,否则跳转到登录页面,是则放行
        Object user = request.getSession().getAttribute("user");
        if (user != null){
            //在线程成员变量ThreadLocal中存入id
            long userId = (long)user;
            ThreadLocalContext.setId(userId);
            log.info("当前用户id为：{}",userId);
            //跳转
            filterChain.doFilter(request,response);
            return;
        }


        //访问了未登录禁止访问的页面，跳转到登录页面
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(RetObj.error("NOTLOGIN")));
        return;
    }

    private boolean checkURI(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
