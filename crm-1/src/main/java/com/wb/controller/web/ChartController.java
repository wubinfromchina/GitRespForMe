package com.wb.controller.web;

import com.wb.pojo.FunnelVO;
import com.wb.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChartController {
    @Autowired
    private TranService tranService;

    @RequestMapping("/workbench/chart/transaction/index.do")
    public String index(){
        //直接跳转页面
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/queryCountTranGroupByStage.do")
    @ResponseBody
    public Object queryCountTranGroupByStage(){

        List<FunnelVO> funnelVOList = tranService.queryCountTranGroupByStage();

        return funnelVOList;
    }
}
