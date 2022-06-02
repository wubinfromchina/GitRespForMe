package com.wb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.reggie.pojo.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
