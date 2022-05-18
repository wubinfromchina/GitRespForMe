package com.wb.controller.setting;

import com.wb.pojo.ReturnObj;
import com.wb.pojo.User;
import com.wb.pojo.utils.Contents;
import com.wb.pojo.utils.DateUtil;
import com.wb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * url和controller方法处理完请求后，响应信息返回的页面资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(HttpSession session,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "settings/qx/user/login";
        }else {
            String username = null;
            String password = null;
            String name = null;
            for (Cookie cookie : cookies) {
                if ("loginAct".equals(cookie.getName())){
                    username = cookie.getValue();
                }else if ("loginPwd".equals(cookie.getName())){
                    password = cookie.getValue();
                }else if ("name".equals(cookie.getName())){
                    name = cookie.getValue();
                }
            }
            if (password != null && password != "" && username != null && username != "" && name != null && name != "") {
                Map<String,Object> map = new HashMap<>();
                map.put("loginAct",username);
                map.put("loginPwd",password);
                User user = userService.selectByIdAndPwd(map);
                session.setAttribute(Contents.LOGIN_SESSION_USER,user);
                return "workbench/index";
            }else {return "settings/qx/user/login";}
        }
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(HttpServletResponse response,HttpSession session, String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userService.selectByIdAndPwd(map);

        ReturnObj returnObj = new ReturnObj();
        returnObj.setRetData(user);
        String msg = "";
        if (user == null){//账号或密码错误,返回登录页面
            msg = "账号或密码错误，请重试";
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg(msg);
        }else {//再次判断其他条件，不符合则返回登录页面
            String nowTime = DateUtil.formatDateTime(new Date());
            if (nowTime.compareTo(user.getExpireTime()) > 0) {//账户到期
                msg = "账户已过期";
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg(msg);
            }else if ("0".equals(user.getLockState())){//状态被锁定
                msg = "账户已锁定";
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg(msg);
            }else if (!user.getAllowIps().contains(request.getRemoteAddr())){//危险IP
                msg = "当前登录地点非常用地点，请重新尝试";
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg(msg);
            }else {//以上条件都满足，则登录成功
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
                returnObj.setMsg(msg);
                session.setAttribute(Contents.LOGIN_SESSION_USER,user);
                //用户勾选记住密码，则写入到cookie中
                Cookie cookie1 = new Cookie("loginAct", user.getLoginAct());
                Cookie cookie2 = new Cookie("loginPwd", user.getLoginPwd());
                if ("true".equals(isRemPwd)){
                    cookie1.setMaxAge(60*60*24*10);
                    cookie2.setMaxAge(60*60*24*10);
                }else {
                    cookie1.setMaxAge(0);
                    cookie2.setMaxAge(0);
                }
                cookie1.setPath("/");
                cookie2.setPath("/");
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
        }
        return returnObj;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //销毁cookie
        Cookie cookie1 = new Cookie("loginAct", "1");
        cookie1.setMaxAge(0);
        cookie1.setPath("/");
        response.addCookie(cookie1);
        Cookie cookie2 = new Cookie("loginPwd", "1");
        cookie2.setMaxAge(0);
        cookie2.setPath("/");
        response.addCookie(cookie2);
        //销毁session
        session.invalidate();

        return "redirect:/";
    }
}
