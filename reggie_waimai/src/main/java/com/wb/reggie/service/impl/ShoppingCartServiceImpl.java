package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.mapper.ShoppingCartMapper;
import com.wb.reggie.pojo.ShoppingCart;
import com.wb.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 购物车业务逻辑
 * @Title: ShoppingCartServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 14:52
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    /**
     * 某个菜品或套餐减一
     * @param shoppingCart
     */
    @Override
    @Transactional
    public void sub(ShoppingCart shoppingCart) {
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        //如果是菜品
        if (shoppingCart.getDishId()!=null && shoppingCart.getSetmealId()==null){
            //查询份数
            wrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
            ShoppingCart one = super.getOne(wrapper);
            if (one.getNumber() > 1){
                //份数大于1则直接减一
                one.setNumber(one.getNumber() - 1);
                super.updateById(one);
            }else {
                //只有一份则直接删除
                super.remove(wrapper);
            }
        }
        //如果是套餐
        if (shoppingCart.getDishId()==null && shoppingCart.getSetmealId()!=null){
            //查询份数
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            ShoppingCart one = super.getOne(wrapper);
            if (one.getNumber() > 1){
                //份数大于1则直接减一
                one.setNumber(one.getNumber() - 1);
                super.updateById(one);
            }else {
                //只有一份则直接删除
                super.remove(wrapper);
            }
        }
    }
}
