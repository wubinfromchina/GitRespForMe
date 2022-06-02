package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.mapper.OrderDetailMapper;
import com.wb.reggie.pojo.OrderDetail;
import com.wb.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Description: 订单菜品业务逻辑层
 * @Title: OrderDetailServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 19:37
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
