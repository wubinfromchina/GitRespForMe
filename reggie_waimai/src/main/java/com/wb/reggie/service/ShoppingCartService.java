package com.wb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.reggie.pojo.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    void sub(ShoppingCart shoppingCart);
}
