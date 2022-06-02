package com.wb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.ShoppingCart;
import com.wb.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 购物车控制层
 * @Title: ShoppingCartController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/6/1 10:09
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 展示购物车
     * @param session
     * @return
     */
    @GetMapping("/list")
    public RetObj<List<ShoppingCart>> list(HttpSession session){
        //获取当前移动端用户id
        long userId = (long) session.getAttribute("user");
        //查询该用户下购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        wrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);

        return RetObj.success(list);
    }

    /**
     * 购物车增加菜品或套餐
     * @param session
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public RetObj<ShoppingCart> add(HttpSession session, @RequestBody ShoppingCart shoppingCart){
        //获取当前用户id
        long userId = (long) session.getAttribute("user");
        //查询是否存在
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        wrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        wrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());

        ShoppingCart one = shoppingCartService.getOne(wrapper);
        if (one == null) {//不存在则新建
            //填充属性
            shoppingCart.setUserId(userId);
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            //添加
            shoppingCartService.save(shoppingCart);
            return RetObj.success(shoppingCart);
        }else {//存在则number+1
            one.setNumber(one.getNumber() + 1);
            //更新
            shoppingCartService.updateById(one);
            return RetObj.success(one);
        }
    }

    /**
     * 某个菜品或套餐减一(先进行判断是菜品还是套餐再调用方法更好)
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public RetObj<String> sub(@RequestBody ShoppingCart shoppingCart){

        shoppingCartService.sub(shoppingCart);

        return RetObj.success("移除成功");
    }

    @DeleteMapping("/clean")
    public RetObj<String> delete(HttpSession session){
        //获取当前用户id
        long userId = (long) session.getAttribute("user");
        //删除该用户下关联的购物车
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);

        shoppingCartService.remove(wrapper);

        return RetObj.success("清空成功");
    }
}
