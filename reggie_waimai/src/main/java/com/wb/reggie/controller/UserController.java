package com.wb.reggie.controller;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.User;
import com.wb.reggie.service.UserService;
import com.wb.reggie.utils.SMSUtils;
import com.wb.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description: 用户管理控制层
 * @Title: UserController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 8:14
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 短信发送服务
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public RetObj<String> sendMsg(HttpSession session, @RequestBody User user){
        log.info("准备获取验证码");
        //获取手机号
        String phone = user.getPhone();
        if (!StringUtils.isEmpty(phone)){
            //使用工具类获取验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            //发送短信
            //SMSUtils.sendMessage("ruiji",code,phone,"");
            log.info("code={}",code);
            //保存验证码至session
            session.setAttribute(phone,code);
            return RetObj.success("短信发送成功");
        }else {
            return RetObj.error("发送失败,手机号有错误");
        }
    }

    /**
     * 移动端用户登录
     * @param session
     * @param map
     * @return
     */
    @PostMapping("/login")
    public RetObj<User> login(HttpSession session, @RequestBody Map map){
        log.info("准备登录");
        //获取手机号和验证码
        String phone = (String)map.get("phone");
        String code = (String)map.get("code");
        //获取session中的code值
        String codeOfSession = (String)session.getAttribute(phone);
        //校验
        if (codeOfSession!=null && code.equals(codeOfSession)){
            //比对成功
            //判断是否为新用户，是则新建用户
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone,phone);
            User user = userService.getOne(wrapper);
            if (user == null) {
                user = new User();
                user.setName("用户"+phone);
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return RetObj.success(user);
        }

        return RetObj.error("登陆失败");
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @PostMapping("/loginout")
    public RetObj<String> loginout(HttpSession session){
        session.removeAttribute("user");
        return RetObj.success("退出登录");
    }
}
