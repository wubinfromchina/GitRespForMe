package com.wb.controller.setting;

import com.wb.pojo.*;
import com.wb.pojo.utils.Contents;
import com.wb.pojo.utils.DateUtil;
import com.wb.pojo.utils.UUIDUtils;
import com.wb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {
    @Autowired
    private ClueService clueService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/toIndex.do")
    public String toIndex(HttpServletRequest request){
        List<User> userList = userService.selectAllUser();


        List<DicValue> appellationList = dicValueService.queryDicByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicByTypeCode("source");

        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);

        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveClue.do")
    @ResponseBody
    public Object saveClue(Clue clue, HttpSession session){
        ReturnObj returnObj = new ReturnObj();
        User user = (User)session.getAttribute(Contents.LOGIN_SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtil.formatDateTime(new Date()));
        clue.setCreateBy(user.getId());
        try {
            int ret = clueService.saveClue(clue);
            if (ret>0){
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg("系统繁忙，请重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请重试");
        }
        return returnObj;
    }

    @RequestMapping("/workbench/clue/getAllClueByCon.do")
    @ResponseBody
    public Object getAllClueByCon(Clue clue,Integer pageNo,Integer pageSize){
        //设置参数
        Map<String,Object> map = new HashMap<>();
        map.put("fullname",clue.getFullname());
        map.put("company",clue.getCompany());
        map.put("phone",clue.getPhone());
        map.put("source",clue.getSource());
        map.put("owner",clue.getOwner());
        map.put("mphone",clue.getMphone());
        map.put("state",clue.getState());
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Clue> clueList = clueService.queryAllClueByCon(map);
        int rows = clueService.queryCountByCon(map);
        //封装返回值响应到前端
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("clueList",clueList);
        retMap.put("totalRows",rows);
        return retMap;

    }

    @RequestMapping("/workbench/clue/selectById.do")
    @ResponseBody
    public Object selectById(String id){
        Clue clue = clueService.queryById(id);
        return clue;
    }

    @RequestMapping("/workbench/clue/deleteByIds.do")
    @ResponseBody
    public Object deleteByIds(String[] id){
        ReturnObj returnObj = new ReturnObj();
        try {
            clueService.dropByIds(id);
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请重试");
        }
        return returnObj;
    }

    @RequestMapping("/workbench/clue/updateByCon.do")
    @ResponseBody
    public Object updateByCon(Clue clue,HttpSession session){
        User user = (User) session.getAttribute(Contents.LOGIN_SESSION_USER);
        clue.setEditBy(user.getId());
        clue.setEditTime(DateUtil.formatDateTime(new Date()));
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = clueService.setClueByCon(clue);
            if (ret>0){
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg("系统繁忙，请重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请重试");
        }
        return returnObj;
    }


    //以下是clue目录下detail页面的控制层

    @RequestMapping("/workbench/clue/detail.do")
    public String selectForDetailById(String id,HttpServletRequest request){
        Clue clue = clueService.queryForDetailById(id);
        request.setAttribute("clue",clue);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryRemarkForDetail(id);
        request.setAttribute("clueRemarkList",clueRemarkList);
        List<Activity> activityList = activityService.queryActByClueId(id);
        request.setAttribute("activityList",activityList);
        return "/workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/selectActByName.do")
    @ResponseBody
    public Object selectActByName(String activityName,String clueId){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("name",activityName);
        map.put("clueId",clueId);
        //调用方法
        List<Activity> activityList = activityService.queryAcctByName(map);
        //返回
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveActClueRelation.do")
    @ResponseBody
    public Object saveActClueRelation(String[] id,String clueId){
        ReturnObj returnObj = new ReturnObj();
        //封装参数
        ClueActivityRelation car = null;
        List<ClueActivityRelation> list = new ArrayList<>();
        for (String aid : id) {
            car = new ClueActivityRelation();
            car.setId(UUIDUtils.getUUID());
            car.setClueId(clueId);
            car.setActivityId(aid);
            list.add(car);
        }
        try {
            int ret = clueActivityRelationService.saveClueActRelationByList(list);
            if (ret>0){
                List<Activity> activityList = activityService.queryActForDetailByIds(id);
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
                returnObj.setRetData(activityList);
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

    @RequestMapping("/workbench/clue/deleteClueActRelationById.do")
    @ResponseBody
    public Object deleteClueActRelationById(ClueActivityRelation relation){
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = clueActivityRelationService.dropClueActRelationById(relation);
            if (ret>0){
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

    @RequestMapping("/workbench/clue/convert.do")
    public String convert(String id,HttpServletRequest request){
        //获取市场活动明细，存储到request作用域
        Clue clue = clueService.queryForDetailById(id);
        request.setAttribute("clue",clue);
        //数据字典值
        List<DicValue> stageList = dicValueService.queryDicByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        return "workbench/clue/convert";
    }

    @RequestMapping("/workbench/clue/selectActByNameClueIdForConvert.do")
    @ResponseBody
    public Object selectActByNameClueIdForConvert(String activityName,String clueId){
        Map<String,Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList = activityService.queryActByNameClueId(map);

        return activityList;
    }

    //转换线索
    @RequestMapping("/workbench/clue/saveConvertClue.do")
    @ResponseBody
    public Object saveConvertClue(HttpSession session,String clueId,String money,String name,String expectedDate,String stage,String activityId,String isCreateTran){
        Map<String,Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        User user = (User) session.getAttribute(Contents.LOGIN_SESSION_USER);
        map.put(Contents.LOGIN_SESSION_USER,user);

        ReturnObj returnObj = new ReturnObj();
        try {
            clueService.saveConvertClue(map);
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请稍后重试");
        }
        return returnObj;
    }

    @RequestMapping("/workbench/clue/saveOneRemark.do")
    @ResponseBody
    public Object saveOneRemark(ClueRemark remark,HttpSession session){
        User user = (User)session.getAttribute(Contents.LOGIN_SESSION_USER);
        remark.setId(UUIDUtils.getUUID());
        remark.setCreateTime(DateUtil.formatDateTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Contents.REMARK_EDIT_FLAG_ZERO);
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = clueRemarkService.saveClueRemark(remark);
            if (ret>0){
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
                remark.setCreateBy(user.getName());
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
/*
    @RequestMapping("/workbench/clue/deleteById")
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

    @RequestMapping("/workbench/clue/modifyRemark.do")
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
    */
}
