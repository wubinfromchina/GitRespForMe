package com.wb.controller.web;

import com.wb.pojo.Activity;
import com.wb.pojo.ActivityRemark;
import com.wb.pojo.ReturnObj;
import com.wb.pojo.User;
import com.wb.pojo.utils.Contents;
import com.wb.pojo.utils.DateUtil;
import com.wb.pojo.utils.UUIDUtils;
import com.wb.pojo.utils.WorkbookUtils;
import com.wb.service.ActivityRemarkService;
import com.wb.service.ActivityService;
import com.wb.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class ActivityController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> list = userService.selectAllUser();
        request.setAttribute("list",list);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/insertOne.do")
    @ResponseBody
    public Object insertOne(HttpSession session,HttpServletRequest request, Activity activity){
        User user = (User) session.getAttribute(Contents.LOGIN_SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtil.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObj returnObj = new ReturnObj();
        try {
            //调用Service,保存创建信息
            int i = activityService.saveActivity(activity);
            if (i > 0) {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
                returnObj.setMsg("系统正在繁忙，请重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统正在繁忙，请重试....");
        }
        return returnObj;
    }

    /**
     * 跟据条件查询所有符合条件的市场活动，并返回总条数
     * @return
     */
    @RequestMapping("/workbench/activity/getAllByCon.do")
    @ResponseBody
    public Object getAllByCon(Activity activity,Integer pageNo,Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("name",activity.getName());
        map.put("owner",activity.getOwner());
        map.put("startDate",activity.getStartDate());
        map.put("endDate",activity.getEndDate());
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Activity> activityList = activityService.queryByPageNoAndPageSize(map);
        Integer rows = activityService.queryByAllCount(map);
        //封装返回值响应到前端
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",rows);
        return retMap;
    }

    /**
     * 根据用户选中的所有条数的id删除activity
     * @param id
     * @return
     */
    @RequestMapping("/workbench/activity/deleteByIds.do")
    @ResponseBody
    public Object deleteByIds(String[] id){
        ReturnObj returnObj = new ReturnObj();
        try {
            Integer retRows = activityService.deleteActivityByIds(id);
            if (retRows>0){
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

    /**
     * 根据用户选中的记录返回用户需修改的参数
     * @param id
     * @return
     */
    @RequestMapping("/workbench/activity/selectById.do")
    @ResponseBody
    public Activity selectById(String id){
        Activity activity = activityService.selectByPrimaryKey(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/updateActivityByAllCon.do")
    @ResponseBody
    public Object updateActivityByAllCon(Activity activity,HttpSession session){
        String nowTime = DateUtil.formatDate(new Date());
        activity.setEditTime(nowTime);
        User user = (User) session.getAttribute(Contents.LOGIN_SESSION_USER);
        activity.setEditBy(user.getId());

        ReturnObj returnObj = new ReturnObj();
        try {
            Integer rows = activityService.updateActivityByAllCon(activity);
            if (rows>0){
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

    public void fileDownload(HttpServletResponse response){
        //1.设置响应类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        FileInputStream is = null;
        OutputStream out = null;
        try {
            //输出流
            out = response.getOutputStream();
            //默认会在浏览器中打开或者选择应用程序打开，除此之外才会弹出下载窗口
            //设置响应头信息，直接激活文件下载窗口
            response.addHeader("Content-Disposition","attachment;filename=**file.xls");
            //读取磁盘excel文件，输出到浏览器
            is = new FileInputStream("");
            byte[] buff = new byte[256];
            int len = 0;
            while ((len=is.read(buff))!=-1){
                out.write(buff,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping("/workbench/activity/allActivityDownload.do")
    public void allActivityDownload(HttpServletResponse response) throws IOException {
        List<Activity> allActList = activityService.getAllAct();

        //工具类获取workbook文件
        HSSFWorkbook workbook = WorkbookUtils.getOutHSSFWorkbook(allActList);
        //设置响应类型
        OutputStream out = WorkbookUtils.getOutputStream(response);

        workbook.write(out);
        workbook.close();

        out.flush();
    }

    @RequestMapping("/workbench/activity/getActByCon.do")
    public void getActByCon(String[] id,HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryActByCon(id);

        //工具类获取workbook文件
        HSSFWorkbook workbook = WorkbookUtils.getOutHSSFWorkbook(activityList);
        //设置响应类型
        OutputStream out = WorkbookUtils.getOutputStream(response);
        workbook.write(out);
        workbook.close();

        out.flush();

    }

    /**
     * 配置springmvc的文件上传解析器
     * @param myFile
     * @return
     */
    @RequestMapping("/workbench/activity/fileUpload.do")
    @ResponseBody
    public Object fileUpload(MultipartFile myFile){
        String filename = myFile.getOriginalFilename();
        ReturnObj returnObj = new ReturnObj();
        int retNum = 0;
        try {
            //读文件拿到workbook对象
            InputStream is = myFile.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            //获取第一页的数据
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;//行
            HSSFCell cell = null;//列
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                for (int j = 1; j < row.getLastCellNum(); j++) {
                    //获取HSSFCell对象，封装了部分信息
                    cell = row.getCell(j);
                    //获取列中的数据,调用工具类方法
                    String cellValue = WorkbookUtils.getStringCellValue(cell);
                    activity = WorkbookUtils.setActivityColumn(activity,j,cellValue);
                }
                activityList.add(activity);
            }
            //调用service层方法
            retNum= activityService.saveByFile(activityList);
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
            returnObj.setMsg("文件上传成功");
            returnObj.setRetData(retNum);
        } catch (IOException e) {
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("文件上传失败");
            returnObj.setRetData(retNum);
        }
        return returnObj;
    }

    @RequestMapping("/workbench/activity/getDetailActivity.do")
    public String getDetailActivity(String id,HttpServletRequest request){
        Activity activity = activityService.queryDetailById(id);
        List<ActivityRemark> activityRemarks = activityRemarkService.queryAllActivityRemark(id);
        request.setAttribute("remarkList",activityRemarks);
        request.setAttribute("activity",activity);
        return "workbench/activity/detail";
    }
}
