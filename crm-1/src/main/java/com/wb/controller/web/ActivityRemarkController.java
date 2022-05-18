package com.wb.controller.web;

import com.wb.pojo.ActivityRemark;
import com.wb.pojo.ReturnObj;
import com.wb.pojo.User;
import com.wb.pojo.utils.Contents;
import com.wb.pojo.utils.DateUtil;
import com.wb.pojo.utils.UUIDUtils;
import com.wb.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {
    @Autowired
    ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveOneRemark.do")
    @ResponseBody
    public Object saveOneRemark(ActivityRemark remark, HttpSession session){
        User user = (User)session.getAttribute(Contents.LOGIN_SESSION_USER);
        remark.setId(UUIDUtils.getUUID());
        remark.setCreateTime(DateUtil.formatDateTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Contents.REMARK_EDIT_FLAG_ZERO);
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = activityRemarkService.saveOneRemark(remark);
            if (ret > 0){
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
                returnObj.setRetData(remark);
            }else {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg("系统繁忙，请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请稍后重试");
        }
        return returnObj;
    }

    @RequestMapping("/workbench/activity/deleteById")
    @ResponseBody
    public Object deleteById(String id){
        ReturnObj returnObj = new ReturnObj();
        try {
            int i = activityRemarkService.deleteByPrimaryKey(id);
            if (i == 1){
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg("系统繁忙，请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请稍后重试");
        }
        return returnObj;
    }

    @RequestMapping("/workbench/activity/modifyRemark.do")
    @ResponseBody
    public Object modifyRemark(ActivityRemark activityRemark,HttpSession session){
        activityRemark.setEditFlag(Contents.REMARK_EDIT_FLAG_ONE);
        activityRemark.setEditTime(DateUtil.formatDateTime(new Date()));
        User user = (User)session.getAttribute(Contents.LOGIN_SESSION_USER);
        activityRemark.setEditBy(user.getId());
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = activityRemarkService.setRemark(activityRemark);
            if (ret>0){
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
                returnObj.setRetData(activityRemark);
            }else {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg("系统繁忙，请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请稍后重试");
        }

        return returnObj;
    }
}
