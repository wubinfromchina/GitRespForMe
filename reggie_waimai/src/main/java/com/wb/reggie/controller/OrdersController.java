package com.wb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wb.reggie.DTO.OrdersDto;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.OrderDetail;
import com.wb.reggie.pojo.Orders;
import com.wb.reggie.service.OrderDetailService;
import com.wb.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Description: 订单控制层
 * @Title: OrdersController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/31 21:35
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 封装了一个订单转换功能
     * @param records
     * @return List<OrdersDto>
     */
    public List<OrdersDto> retList(List<Orders> records){
        return records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item,ordersDto);
            //订单明细条件构造器
            LambdaQueryWrapper<OrderDetail> orderDetailWrapper = new LambdaQueryWrapper<>();
            orderDetailWrapper.eq(OrderDetail::getOrderId,item.getId()).orderByDesc(OrderDetail::getAmount);
            ordersDto.setOrderDetails(orderDetailService.list(orderDetailWrapper));
            return ordersDto;
        }).collect(Collectors.toList());
    }

    /**
     * 客户端分页显示
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public RetObj<Page> page(int page, int pageSize, String number, String beginTime, String endTime){
        log.info("准备分页中");
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>(page,pageSize);
        //查询所有符合条件的订单
        LambdaQueryWrapper<Orders> ordersWrapper = new LambdaQueryWrapper<>();
        ordersWrapper.like(number!=null,Orders::getNumber,number);
        ordersWrapper.between(beginTime!=null && endTime!=null,Orders::getCheckoutTime,beginTime,endTime);
        ordersWrapper.orderByDesc(Orders::getCheckoutTime);
        //分页查询
        ordersService.page(pageInfo,ordersWrapper);
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");

        //转换订单
        List<OrdersDto> list =  this.retList(pageInfo.getRecords());

        //填充属性
        ordersDtoPage.setRecords(list);

        return RetObj.success(ordersDtoPage);
    }

    /**
     * 移动端分页显示
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public RetObj<Page> userPage(HttpSession session,int page,int pageSize){
        //用户id
        long userId = (long) session.getAttribute("user");

        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> ordersWrapper = new LambdaQueryWrapper<>();
        ordersWrapper.eq(Orders::getUserId,userId);
        ordersWrapper.orderByDesc(Orders::getCheckoutTime).orderByDesc(Orders::getOrderTime);
        //查询该用户下所有订单
        ordersService.page(pageInfo,ordersWrapper);
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");
        //转换订单
        List<OrdersDto> list =  this.retList(pageInfo.getRecords());

        ordersDtoPage.setRecords(list);

        return RetObj.success(ordersDtoPage);
    }

    /**
     * 移动端提交订单
     * @return
     */
    @PostMapping("/submit")
    public RetObj<String> submit(HttpSession session, @RequestBody Orders orders){
        log.info("准备下单");
        //封装参数
        orders.setUserId((long)session.getAttribute("user"));
        //提交订单
        ordersService.submit(orders);

        return RetObj.success("下单成功");
    }

    /**
     * 派送
     * @param orders
     * @return
     */
    @PutMapping
    public RetObj<String> editStatus(@RequestBody Orders orders){
        //修改订单状态
        Orders ordersChange = ordersService.getById(orders.getId());
        ordersChange.setStatus(orders.getStatus());

        //保存
        ordersService.updateById(ordersChange);
        return RetObj.success("派送成功");
    }
}
