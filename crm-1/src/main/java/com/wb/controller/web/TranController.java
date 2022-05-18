package com.wb.controller.web;

import com.wb.pojo.*;
import com.wb.pojo.utils.Contents;
import com.wb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class TranController {
    @Autowired
    private TranService tranService;
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranRemarkService tranRemarkService;
    @Autowired
    private TranHistoryService tranHistoryService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.selectAllUser();
        List<DicValue> sourceList = dicValueService.queryDicByTypeCode("source");
        List<DicValue> typeList = dicValueService.queryDicByTypeCode("transactionType");
        List<DicValue> stageList = dicValueService.queryDicByTypeCode("stage");

        request.setAttribute("userList",userList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("typeList",typeList);
        request.setAttribute("stageList",stageList);
        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/tran/getAllTranByCon.do")
    @ResponseBody
    public Object getAllTranByCon(Tran tran,Integer pageNo,Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("owner",tran.getOwner());
        map.put("name",tran.getName());
        map.put("customerId",tran.getCustomerId());
        map.put("stage",tran.getStage());
        map.put("type",tran.getType());
        map.put("source",tran.getSource());
        map.put("contactsId",tran.getContactsId());
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);

        List<Tran> tranList = tranService.queryAllTranByCon(map);
        Integer rows = tranService.queryCountByCon(map);

        Map<String,Object> retMap = new HashMap<>();
        retMap.put("tranList",tranList);
        retMap.put("totalRows",rows);
        return retMap;
    }

    @RequestMapping("/workbench/transaction/toSave.do")
    public String toSave(HttpServletRequest request){
        List<User> userList = userService.selectAllUser();
        List<DicValue> sourceList = dicValueService.queryDicByTypeCode("source");
        List<DicValue> typeList = dicValueService.queryDicByTypeCode("transactionType");
        List<DicValue> stageList = dicValueService.queryDicByTypeCode("stage");

        request.setAttribute("userList",userList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("typeList",typeList);
        request.setAttribute("stageList",stageList);
        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/searchAct.do")
    @ResponseBody
    public Object searchAct(String activityName){
        List<Activity> activityList = activityService.queryActByName(activityName);

        return activityList;
    }

    @RequestMapping("/workbench/transaction/searchContacts.do")
    @ResponseBody
    public Object searchContacts(String contactsName){
        List<Contacts> contactsList = contactsService.queryAllConByName(contactsName);
        return contactsList;
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue){
        //读配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stageValue);
        //返回匹配的可能性
        return possibility;
    }

    @RequestMapping("/workbench/transaction/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String customerName){
        List<String> customerList = customerService.queryAllCustomer(customerName);
        return customerList;
    }

    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    @ResponseBody
    public Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
        //封装参数
        map.put(Contents.LOGIN_SESSION_USER,session.getAttribute(Contents.LOGIN_SESSION_USER));
        ReturnObj returnObj = new ReturnObj();
        try {
            //保存数据
            tranService.saveTran(map);
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请稍后重试");
        }
        return returnObj;
    }

    //detail页面
    @RequestMapping("/workbench/transaction/doDetail.do")
    public String toDetail(String id,HttpServletRequest request){
        Tran tran = tranService.queryTranById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkByTranId(id);
        List<TranHistory> historyList = tranHistoryService.queryAllByTranId(id);
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());
        List<DicValue> stageList = dicValueService.queryDicByTypeCode("stage");

        request.setAttribute("stageList",stageList);
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        request.setAttribute("possibility",possibility);
        return "workbench/transaction/detail";
    }

    //edit页面
    @RequestMapping("/workbench/transaction/toEdit.do")
    public String toEdit(String id,HttpServletRequest request){
        Tran tran = tranService.queryTranById(id);
        request.setAttribute("tran",tran);
        List<User> userList = userService.selectAllUser();
        List<DicValue> sourceList = dicValueService.queryDicByTypeCode("source");
        List<DicValue> typeList = dicValueService.queryDicByTypeCode("transactionType");
        List<DicValue> stageList = dicValueService.queryDicByTypeCode("stage");
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());

        request.setAttribute("userList",userList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("typeList",typeList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("possibility",possibility);
        return "workbench/transaction/edit";
    }
    @RequestMapping("/workbench/transaction/saveEditTran.do")
    @ResponseBody
    public Object saveEditTran(@RequestParam Map<String,Object> map, HttpSession session){
        //封装参数
        map.put(Contents.LOGIN_SESSION_USER,session.getAttribute(Contents.LOGIN_SESSION_USER));
        Tran tran = tranService.queryCreateBy((String) map.get("id"));
        map.put("createBy",tran.getCreateBy());
        ReturnObj returnObj = new ReturnObj();
        try {
            //保存数据
            tranService.editTran(map);
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Contents.RETURN_OBJECT_CODE_FAILED);
            returnObj.setMsg("系统繁忙，请稍后重试");
        }
        return returnObj;
    }
}
